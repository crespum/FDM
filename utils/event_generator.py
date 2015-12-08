# coding=utf-8
import time, json, io, datetime, argparse

item_type = ('EVENT', 'INFO', 'AD')
categories = ('pregon', 'music', 'food', 'sport', 'art', 'fire', 'band')
places = {
    'Alameda':(41.903501, -8.866704), 
    'Auditorio de San Bieito':(41.899915, -8.873203), 
    'A Cruzada':(41.897817, -8.874520), 
    'As Solanas':(41.9038126, -8.8659001),
    'Rúas de A Guarda':(-1, -1),
    'Porto':(41.898983, -8.874545),
    'O Fuscalho':(41.902495, -8.879410),
    'Centro Cultural':(41.902892, -8.865532),
    'Estadio A Sangriña':(41.899626, -8.861348),
    'Montiño':(41.900999, -8.866232),
    'Salcidos':(41.909254, -8.852916),
    'Plaza do Reló':(41.9013013,-8.8744885),
    'Praia do Muíño':(41.8748281,-8.8674021),
    'Colexio dos Xesuítas':(41.8883961,-8.8515421,17),
    'Singular Lounge Disco':(41.902339,-8.869759),
    'Atalaia':(41.9026239,-8.8800699),
    'As de Copas':(41.902227,-8.869925,17),
    'Santa Trega':(41.8929508,-8.8737453),
    'San Caetano':(41.8945184,-8.8770014),
    'Recreo artístico guardés':(41.903213,-8.87437),
    'O Coruto':(41.9062441,-8.8620104),
    'O Rosal':(41.936970, -8.836869),
    'Praia da Lamiña':(41.877793, -8.861384),
    'A Guía':(41.905326, -8.876671),
    'Praza dos Seixiños (A Gándara)':(41.915780, -8.847085),
    'A Sangriña':(41.900790, -8.862902),
    'Castelo de Santa Cruz':(41.904506, -8.872801)
    }
"""
An event can have the following fields
event = {
    'EVENT_NAME':'',             # Mandatory
    'DAY':'',                    # Mandatory dd/MM/yyyy
    'START_TIME':'',             # Mandatory hh:mm
    'END_TIME':'',
    'CATEGORY':'',
    'PLACE':'',                  # Mandatory
    'LATITUDE':'',
    'LONGITUDE':'',
    'DESCRIPTION':'',
    # New fields
    'PRICE':'',
    'IMG_URL':'',
    'URL':'',
    'TYPE':''
}
"""

def printDict(d):
    for ind, key in enumerate(d):
        print(str(ind) + " - " + key)

def printList(l):
    for ind, item in enumerate(l):
        print(str(ind) + " - " + item)

def getKey(ind, d):
    # Convert dictionary keys in a tuple so they can be accessed with an index
    keys = ()
    for item in d:
        keys = keys + (item,)

    return keys[ind]

def readItemsFile():
    with open("proba.txt", "r") as myfile:
        events = json.load(myfile)

    # All day events are coded this way to be able to use sort function
    for item in events:
        if item['START_TIME'] == 'Todo o día':
            item['START_TIME'] = '00:02'

    return events

def writeItemsFile(events):
    events = sorted(events, key=lambda event: time.strptime(event['START_TIME'] + ' ' + event['DAY'], "%H:%M %d/%m/%Y"))

    for item in events:
        if item['START_TIME'] == '00:02':
            item['START_TIME'] = 'Todo o día'

    with io.open("proba.txt", "w") as myfile:
        json.dump(events, myfile, ensure_ascii=False)

def removeOldEvents():
    events = readItemsFile()

    # Remove events from previous days
    today = datetime.datetime.now().replace(hour=00, minute=00)
    events = list(filter(lambda item: datetime.datetime.strptime(item['START_TIME'] + ' ' + item['DAY'], "%H:%M %d/%m/%Y") > today, events))

    writeItemsFile(events)

def addItem():
    events = readItemsFile()

    while True:
        new_event = {}

        print("Tipos de eventos: ")
        printList(item_type)
        new_event['TYPE'] = item_type[int(input("Seleccione un número: "))]

        new_event['EVENT_NAME'] = input("Evento: ")
        new_event['DAY'] = input("Data dd/MM/yyyy: ")
        new_event['START_TIME'] = input("Hora de inicio (hh:mm) (vacío se dura todo o día): ")
        if new_event['START_TIME'] == '':
            new_event['START_TIME'] = '00:02'

        if new_event['TYPE'] == 'INFO' or new_event['TYPE'] == 'AD':
            event_url = input("Enlace á información: ")
            if event_url is not '':
                new_event['URL'] = event_url

            icon_img_url = input("URL da imaxe do icono: ")
            if icon_img_url is not '':
                new_event['IMG_URL'] = icon_img_url


        if new_event['TYPE'] == 'EVENT':
            print("Tipos de eventos: ")
            printList(categories)
            new_event['CATEGORY'] = categories[int(input("Seleccionar categoría: "))]

            print("Lugares: ")
            printDict(places)
            new_event['PLACE'] = getKey(int(input("Seleccionar lugar: ")), places)
            if new_event['PLACE'] in places:
                new_event['LATITUDE'] = str(places[new_event['PLACE']][0])
                new_event['LONGITUDE'] = str(places[new_event['PLACE']][1])


            description = input("Descrición: ")
            if description is not '':
                new_event['DESCRIPTION'] = description

            price = input("Precio: ")
            if price is not '':
                new_event['PRICE'] = price

            header_img = input("URL da imaxe de cabeceira: ")
            if header_img is not '':
                new_event['IMG_URL'] = header_img

            event_url = input("URL do evento: ")
            if event_url is not '':
                new_event['URL'] = event_url

        print('Engadir o seguinte evento? ')
        print(new_event)
        if input('Engadir? (s/n): ') == 's':
            events.append(new_event)

        if input('Continuar? (s/n): ') == 'n':
            break;

    writeItemsFile(events)

# Parsing arguments
parser = argparse.ArgumentParser(description='Manage events (add or remove)')
parser.add_argument('-r', '--remove', help='Remove old events', action='store_true')
args = parser.parse_args()

if args.remove:
    removeOldEvents()
else:
    addItem()