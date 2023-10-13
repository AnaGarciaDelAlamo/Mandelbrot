# Mandelbrot

Mi repositorio: https://github.com/AnaGarciaDelAlamo/Mandelbrot.git

Referencia: https://www.linkedin.com/pulse/visualizador-del-conjunto-de-mandelbrot-sergio-mart%C3%AD-torregrosa

Objetivo: El propósito de esta práctica es mejorar la eficiencia y el rendimiento del programa de cálculo del conjunto de Mandelbrot utilizando la concurrencia y los ejecutores de Java. Implementarás una función que divide el cálculo del conjunto entre múltiples procesos, permitiendo una renderización más rápida y eficiente.

Antes de comenzar, es esencial comprender qué es el conjunto de Mandelbrot. Es un conjunto fractal que fue descubierto en 1980 por Benoît Mandelbrot. Está definido por un conjunto de puntos en el plano complejo cuya frontera produce una figura fractal. La definición matemática del conjunto de Mandelbrot es bastante sencilla, pero las imágenes que produce son increíblemente complejas y bellas.

Ahora vamos a desglosar las tareas que debes realizar:

Modificación de la Interfaz de Usuario: Añade un componente de spinner a la interfaz de usuario del programa. Este spinner permitirá al usuario seleccionar el número de trabajadores (procesos) que se encargarán de calcular el conjunto de Mandelbrot.

Actualización del método pintaMandelbrot: Este método se debe modificar para que pueda dividir y distribuir el trabajo entre los 'n' procesos seleccionados por el usuario. Se sugiere dividir el cálculo en sentido horizontal o vertical, dividiendo panel.getWidth() o panel.getHeight() entre el número de procesos respectivamente.

La parte visual de la generación del conjunto de Mandelbrot es un proceso iterativo. Cada punto en el conjunto se obtiene aplicando una fórmula simple repetidamente a un número complejo inicial. El color de cada punto se determina según la cantidad de iteraciones que se necesiten para que el punto escape a la infinitud. Si el punto nunca escapa, se le asigna un color específico (generalmente negro).

Creación de Procesos: Debes crear los procesos (tareas) que realizarán el cálculo del conjunto de Mandelbrot. Cada proceso debe recibir todos los valores necesarios para su cálculo, incluyendo los valores de x1, y1, x2, y2 para saber la parte del conjunto que se está pintando, y las coordenadas de pantalla que le corresponden calcular.

Ejecución y Sincronización de Procesos: Deberás crear un ExecutorService en Java para manejar la ejecución de los procesos. Una vez que los procesos han sido configurados e iniciados, el programa deberá esperar a que todos terminen antes de continuar.

Renderizado de Resultados: Cuando todos los procesos han terminado, el programa debe recolectar y combinar sus resultados. Luego, pintará los valores calculados por todos los procesos en el panel de la interfaz de usuario.

Creación de un Proceso Específico para la Pintura: Además, deberás crear un proceso que se encargue de pintar una parte del conjunto que le indicará el programa principal cuando lo cree.

Al final de este proyecto, deberá tener un programa de cálculo del conjunto de Mandelbrot que sea capaz de dividir el cálculo entre varios procesos para mejorar la eficiencia y el rendimiento. Deberá demostrar la correcta implementación de la concurrencia y los ejecutores en Java.
