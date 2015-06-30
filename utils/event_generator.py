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
    'HEADER_IMG':'-1',
    'EVENT_URL':'-1',
    'TYPE':'0'
}

def printDict(dict):
    pass

def printList(list):
    pass

with open("proba.txt", "r") as myfile:
    events = json.load(myfile)

while True:
    new_event = event

    print("Tipos de eventos: ")
    print(printList(item_type))
    new_event['TYPE'] = item_type(input("Seleccione un número: "))

    new_event['EVENT_NAME'] = input("Nome do evento: ")
    new_event['DAY'] = input("Data dd/MM/yyyy: ")
    new_event['START_TIME'] = input("Hora de inicio (hh:mm): ")

    print("Tipos de eventos: ")
    print(printList(categories))
    new_event['CATEGORY'] = input("Seleccionar categoría: ")

    print("Lugares: ")
    print(printDict(categories))
    new_event['PLACE'] = input("Lugar (vacío se non está decidido): ")
    if new_event['PLACE'].lower() in places:
        new_event['LATITUDE'] = places[new_event['PLACE'].lower()][0]
        new_event['LONGITUDE'] = places[new_event['PLACE'].lower()][1]

    new_event['DESCRIPTION'] = input("Descrición: ")
    new_event['PRICE'] = input("Precio: ")
    new_event['HEADER_IMG'] = input("URL da imaxe de cabeceira: ")
    new_event['EVENT_URL'] = input("URL do evento: ")

    self.events.append(new_event)
    if input('Continuar? (s/n): ') == 'n':
        break;

self.events = sorted(self.events, key=lambda event: time.strptime(event['DAY'], "%d/%m"))
with open("proba.txt", "w") as myfile:
    json.dump(self.events, myfile)

