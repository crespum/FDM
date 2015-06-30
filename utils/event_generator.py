# coding=utf-8
import time, json

class FDM():
    categories = ('pregon', 'music', 'food', 'sport', 'art', 'fire')
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
        'EVENT_NAME':'',
        'DAY':'',
        'START_TIME':'',
        'END_TIME':'',
        'CATEGORY':'',
        'PLACE':'',
        'LATITUDE':'',
        'LONGITUDE':'',
        'DESCRIPTION':'',
    }

    def __init__(self):
        with open("proba.txt", "r") as myfile:
            self.events = json.load(myfile)

    def setName(self, val):
        pass

    def readValues(self):
        event = {}
        event['EVENT_NAME'] = input("Nome do evento: ")
        event['DAY'] = input("Día: ")
        event['START_TIME'] = input("Hora de inicio: ")
        event['END_TIME'] = input("Hora de fin (vacío se non ten): ")
        event['CATEGORY'] = input("Seleccionar categoría: ")
        event['PLACE'] = input("Lugar (vacío se non está decidido): ")
        if event['PLACE'].lower() not in places:
            event['LATITUDE'] = input("Latitude: ")
            event['LONGITUDE'] = input("Longitude: ")
        else:
            print("Lugar conocido!")
            event['LATITUDE'] = places[event['PLACE'].lower()][0]
            event['LONGITUDE'] = places[event['PLACE'].lower()][1]

        event['DESCRIPTION'] = input("Descrición: ")
        self.events.append(event)
        if input('Continuar? (s/n): ') == 'n':
            pass
    
    def addEvent(self):
        print(self.event['EVENT_NAME'])

    def stopEditing(self):         
        self.events = sorted(self.events, key=lambda event: time.strptime(event['DAY'], "%d/%m"))
        with open("proba.txt", "w") as myfile:    
            json.dump(self.events, myfile)