# coding=utf-8
import time, json

item_type = ('event', 'info', 'ad')
categories = ('pregon', 'music', 'food', 'sport', 'art', 'fire', 'band')
places = {
    'alameda':(41.903501, -8.866704), 
    'auditorio de san bieito':(41.899915, -8.873203), 
    'a cruzada':(41.897817, -8.874520), 
    'as solanas':(41.905435, -8.878922),
    'rúas da guarda':(-1, -1),
    'porto':(41.898983, -8.874545),
    'o fuscalho':(41.902495, -8.879410),
    'centro cultural':(41.902892, -8.865532),
    'estadio da sangriña':(41.899626, -8.861348),
    'montiño':(41.900999, -8.866232),
    'salcidos':(41.909254, -8.852916)
    }
event = {
    'EVENT_NAME':'-1',             # Mandatory
    'DAY':'-1',                    # Mandatory dd/MM/yyyy
    'START_TIME':'-1',             # Mandatory hh:mm
    'END_TIME':'-1',
    'CATEGORY':'-1',
    'PLACE':'-1',                  # Mandatory
    'LATITUDE':'-1',
    'LONGITUDE':'-1',
    'DESCRIPTION':'-1',
    # New fields
    'PRICE':'-1',
    'IMG_URL':'-1',
    'EVENT_URL':'-1',
    'TYPE':'0'
}

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

with open("proba.txt", "r") as myfile:
    events = json.load(myfile)

events = sorted(events, key=lambda event: time.strptime(event['START_TIME'] + ' ' + event['DAY'], "%H:%M %d/%m/%Y"))
with open("proba.txt", "w") as myfile:
    json.dump(events, myfile)


while True:
    new_event = event

    print("Tipos de eventos: ")
    printList(item_type)
    new_event['TYPE'] = item_type[int(input("Seleccione un número: "))]

    if new_event['TYPE'] == 'INFO' or new_event['TYPE'] == 'AD':
        new_event['EVENT_NAME'] = input("Información a mostrar: ")

        event_url = input("Enlace á información: ")
        if event_url is not '':
            new_event['EVENT_URL'] = event_url

        icon_img_url = input("URL da imaxe do icono: ")
        if icon_img_url is not '':
            new_event['IMG_URL'] = icon_img_url


    if new_event['TYPE'] == 'EVENT':
        new_event['EVENT_NAME'] = input("Nome do evento: ")
        new_event['DAY'] = input("Data dd/MM/yyyy: ")
        new_event['START_TIME'] = input("Hora de inicio (hh:mm): ")

        print("Tipos de eventos: ")
        printList(categories)
        new_event['CATEGORY'] = categories[int(input("Seleccionar categoría: "))]

        print("Lugares: ")
        printDict(places)
        new_event['PLACE'] = getKey(int(input("Seleccionar lugar: ")), places)
        if new_event['PLACE'].lower() in places:
            new_event['LATITUDE'] = places[new_event['PLACE'].lower()][0]
            new_event['LONGITUDE'] = places[new_event['PLACE'].lower()][1]


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
            new_event['EVENT_URL'] = event_url

    print('Engadir o seguinte evento? ')
    print(new_event)
    if input('Engadir? (s/n): ') == 's':
        events.append(new_event)

    if input('Continuar? (s/n): ') == 'n':
        break;

events = sorted(events, key=lambda event: time.strptime(event['START_TIME'] + ' ' + event['DAY'], "%H:%M %d/%m/%Y"))
with open("proba.txt", "w") as myfile:
    json.dump(events, myfile)

