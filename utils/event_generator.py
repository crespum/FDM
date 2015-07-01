# coding=utf-8
import time, json, io

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
    'Salcidos':(41.909254, -8.852916)
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

with open("Eventos.txt", "r") as myfile:
    events = json.load(myfile)

while True:
    new_event = {}

    print("Tipos de eventos: ")
    printList(item_type)
    new_event['TYPE'] = item_type[int(input("Seleccione un número: "))]

    new_event['EVENT_NAME'] = input("Evento: ")
    new_event['DAY'] = input("Data dd/MM/yyyy: ")
    new_event['START_TIME'] = input("Hora de inicio (hh:mm) (00:02 se dura todo o día): ")

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

events = sorted(events, key=lambda event: time.strptime(event['START_TIME'] + ' ' + event['DAY'], "%H:%M %d/%m/%Y"))

with io.open("Eventos.txt", "w") as myfile:
    json.dump(events, myfile, ensure_ascii=False)

