from mesa import Agent, Model
from mesa.time import BaseScheduler

class CalculadoraAgent(Agent):
    def __init__(self, id_unico, modelo, tipo_operacion):
        super().__init__(id_unico, modelo)
        self.tipo_operacion = tipo_operacion

    def realizar_operacion(self, x, y):
        if self.tipo_operacion == 'suma':
            return x + y
        elif self.tipo_operacion == 'resta':
            return x - y
        elif self.tipo_operacion == 'multiplicacion':
            return x * y
        elif self.tipo_operacion == 'division':
            if y != 0:
                return x / y
            else:
                return "Error: División por 0"
        elif self.tipo_operacion == 'potencia':
            return x ** y

class ModeloCalculadora(Model):
    def __init__(self):
        super().__init__()  
        self.scheduler = BaseScheduler(self)
        # agentes para cada tipo de operación
        self.agent_suma = CalculadoraAgent(1, self, 'suma')
        self.agent_resta = CalculadoraAgent(2, self, 'resta')
        self.agent_multiplicacion = CalculadoraAgent(3, self, 'multiplicacion')
        self.agent_division = CalculadoraAgent(4, self, 'division')
        self.agent_potencia = CalculadoraAgent(5, self, 'potencia')
        # Agregar agentes al programador
        self.scheduler.add(self.agent_suma)
        self.scheduler.add(self.agent_resta)
        self.scheduler.add(self.agent_multiplicacion)
        self.scheduler.add(self.agent_division)
        self.scheduler.add(self.agent_potencia)


    def ejecutar_calculo(self, operacion, x, y):
        if operacion == '+':
            return self.agent_suma.realizar_operacion(x, y)
        elif operacion == '-':
            return self.agent_resta.realizar_operacion(x, y)
        elif operacion == '*':
            return self.agent_multiplicacion.realizar_operacion(x, y)
        elif operacion == '/':
            return self.agent_division.realizar_operacion(x, y)
        elif operacion == '**':
            return self.agent_potencia.realizar_operacion(x, y)
        else:
            return "Operación no reconocida"

# funcion para interactuar con el usuario
def iniciar_calculadora():
    calculadora = ModeloCalculadora()

    while True:
        entrada = input("Introduce una operación (formato: x operador y, o 'salir' para terminar): ")
        if entrada.lower() == 'salir':
            break

        try:
            a, operador, b = entrada.split()
            a, b = float(a), float(b)
            resultado = calculadora.ejecutar_calculo(operador, a, b)
            print(f"Resultado: {resultado}")
        except ValueError:
            print("Error: formato de entrada no valido")
        except Exception as e:
            print(f"Error en la expresion: {e}")

# ejecutar la calculadora
if __name__ == "__main__":
    iniciar_calculadora()
