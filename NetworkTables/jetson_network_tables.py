from networktables import NetworkTables

NetworkTables.initialize(server='10.52.12.2')

vision = NetworkTables.getTable('vision')

def test():
    """
    Tests the network with a simple put/get and returns if it succeeded or failed
    """
    test = NetworkTables.getTable('test')
    test.putNumber('test', 1.0)

    if test.getNumber('test', 0) == 1.0:
        return True
    else:
        return False
