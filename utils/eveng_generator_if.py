import sys
from PySide import QtGui
from event_generator import FDM
from functools import partial

class FDM_GUI(QtGui.QWidget):
    
    def __init__(self, fdm):
        super(FDM_GUI, self).__init__()
        self.fdm = fdm
        self.initUI()
        
    def initUI(self):
        QtGui.QToolTip.setFont(QtGui.QFont('SansSerif', 10))
        
        self.lName = QtGui.QLabel("Nome do evento", self)
        self.lName.move(20, 20)
        self.leTitle = QtGui.QLineEdit(self)
        self.leTitle.move(20, 40)
        self.leTitle.textChanged.connect(partial(self.assign, 'EVENT_NAME'))

        self.btnNew = QtGui.QPushButton('Novo', self)
        self.btnNew.move(20, 80)
        self.btnNew.clicked.connect(self.fdm.addEvent)

        self.btnStop = QtGui.QPushButton('Finalizar', self)
        self.btnStop.move(120, 80)


        self.setGeometry(300, 300, 290, 150)
        self.setWindowTitle('Icon')
        #self.setWindowIcon(QtGui.QIcon('web.png'))
        self.show()

    def assign(self, var, val):
        self.fdm.event[var] = val
        
def main():
    
    app = QtGui.QApplication(sys.argv)
    fdm = FDM()
    ex = FDM_GUI(fdm)
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()