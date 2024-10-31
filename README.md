# Parcial-Paradigmas-2-Samuel Chaves Mora
# PUNTO 1 (PERCEPTRON EN NETLOGO)
Un perceptrón es una neurona artificial utilizada en redes neuronales, diseñada para imitar el comportamiento de las neuronas en el cerebro. En este sentido, el perceptrón realiza cálculos para identificar características en los datos de entrada mediante un algoritmo de aprendizaje, que permite a estas neuronas artificiales aprender y analizar los distintos elementos dentro de una colección de datos. Este proceso de aprendizaje implica entrenar al algoritmo para que haga predicciones, lo cual requiere que el algoritmo se alimente con datos.

El perceptrón funciona como una función matemática: los datos de entrada se multiplican por coeficientes de peso, y el resultado es un valor que puede ser positivo o negativo. La neurona se activa únicamente si el valor es positivo y si el peso calculado de la entrada supera un umbral específico. Luego, el resultado de la predicción se compara con valores conocidos y, si hay discrepancias, el error se retropropaga para ajustar los pesos.

Un perceptrón tiene tres componentes principales:
1) **Nodos de entrada**: que reciben valores de entrada, en este caso, 1 y -1.
2) **Nodo de sesgo**: que siempre tiene una activación de 1.
3) **Nodo de salida**: que recibe los valores de la entrada multiplicados por sus pesos respectivos y produce una salida binaria dependiendo de si la suma supera o no el umbral determinado.

![image](https://github.com/user-attachments/assets/81c0d1af-7879-4a7e-a5e1-917127d4d970)



Para la implementación utilicé variables globales. La variable epoch-error almacena el error acumulado durante el proceso de entrenamiento, mientras que input-node-1 e input-node-2 contienen referencias a los nodos de entrada. Cada enlace tiene un peso asignado y cada nodo (representado por tortugas en el espacio 2D) tiene una activación que indica su estado. Tanto los nodos de entrada como el de salida cuentan con un umbral (threshold) que determina el punto en el que se activan.

En el procedimiento setup, configuro el entorno visual, asignando colores y formas a los nodos. Primero, creo el nodo de salida (perceptrón) con una activación inicial aleatoria y un nodo de sesgo (bias) con activación fija de 1, conectado al perceptrón. Posteriormente, añado los nodos de entrada, que se activan de manera aleatoria en 1 o -1.

Durante la fase de entrenamiento, el perceptrón ajusta los pesos basándose en ejemplos de entrenamiento. Los pesos se modifican de acuerdo con el error entre la salida calculada y el valor objetivo, lo que permite que el modelo aprenda a clasificar los datos siguiendo funciones lógicas (como XOR, OR, AND, etc.). Estas funciones lógicas sirven de referencia para el aprendizaje del perceptrón y establecen los objetivos de activación para los nodos de entrada.

EL CODIGO ESTA UBICADO EN LA PARTE DE LOS ARCHIVOS SE LLAMA PERCEPTRON
CAPTURAS DE PRUEBAS
![Imagen de WhatsApp 2024-10-30 a las 22 40 14_9f5273c7](https://github.com/user-attachments/assets/86e3aa75-81c2-42b4-bdf5-94ddd6010601)
![Imagen de WhatsApp 2024-10-30 a las 22 41 29_9e425ee9](https://github.com/user-attachments/assets/d45a4385-cc69-4a7a-baa1-2548fa687a6d)


# PUNTO 2 (CALCULADORA BASADA EN AGENTES)
Para la implementación, importé las clases necesarias del paquete *mesa*, una biblioteca de Python que facilita la creación de modelos de simulación. En particular, importé *Agent* para crear los agentes y *Model* para construir el modelo general. También añadí *BaseScheduler* de *mesa.time* para gestionar la programación de los agentes.

Luego, creé una clase llamada *CalculadoraAgent*, que hereda de *Agent*. Esta clase representa un agente que puede llevar a cabo distintas operaciones matemáticas. Dentro del constructor *\_\_init\_\_*, inicialicé el agente con un identificador único (*id_unico*), el modelo al que pertenece (*modelo*), y el tipo de operación que puede realizar (*tipo_operacion*). Posteriormente, implementé el método *realizar_operacion*, que toma dos números (*x* e *y*) y ejecuta la operación correspondiente de acuerdo con el *tipo_operacion* del agente, incluyendo operaciones de suma, resta, multiplicación, división y potencia.

Después, definí la clase *ModeloCalculadora*, que hereda de *Model*. Esta clase representa el modelo completo que contiene todos los agentes. En el constructor de este modelo, inicialicé el programador (*scheduler*) y creé instancias de *CalculadoraAgent* para cada operación matemática. A continuación, implementé el método *ejecutar_calculo*, que recibe un operador y dos números, y luego llama al agente correspondiente para realizar la operación.

Finalmente, creé una función llamada *iniciar_calculadora*, que permite a los usuarios interactuar con la calculadora. Esta función solicita al usuario que ingrese una operación, y si el usuario escribe "salir", el programa finaliza.

CAPTURA DE COMO FUNCIONA DESDE REPLIT(SIRVE EN TODO)
