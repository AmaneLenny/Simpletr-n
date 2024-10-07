
package edu.KurtP.Simpletron;

import java.util.Scanner;

/**
 * @author Kurt P
 * @version 1.0.0.01082013
 */
public class Simpletron extends SimpletronOperationCodes {

    private int[] memory = new int[1000];  // Cambio: memoria extendida a 1000
    private int accumulator;
    private int instructionCounter;
    private int instructionRegister;
    private int operationCode;
    private int operand;
    private boolean run = true;

    /**
     * The <code>run</code> method will start Simpletron, display the welcome
     * message and then go straight into SML execution.
     */
    public void run() {
        welcomeMessage();
        execute();
    }

    private void welcomeMessage() {
        System.out.println("***            Welcome to Simpletron!           ***");
        System.out.println("*** Please enter your program, one instruction  ***");
        System.out.println("*** (or data word) at a time. I will display    ***");
        System.out.println("*** the location number and a question mark (?) ***");
        System.out.println("*** You then type the word for that location.   ***");
        System.out.println("*** Type -99999 to stop entering your program.  ***");
    }

    private void execute() {
        Scanner codeInputter = new Scanner(System.in);
        int instructionInput = 0;
        int memoryPointer = 0;

        do {
            System.out.printf("%02d ? ", memoryPointer);
            instructionInput = codeInputter.nextInt();
            memory[memoryPointer] = instructionInput;
            memoryPointer++;
        } while (instructionInput != -99999);

        System.out.println();

        while (run) {
            instructionRegister = memory[instructionCounter];
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;

            switch (operationCode) {
                case READ:
                    System.out.print("Enter an integer: ");
                    memory[operand] = codeInputter.nextInt();
                    break;
                case WRITE:
                    System.out.printf("Memory[%d]: %d%n", operand, memory[operand]);
                    break;
                case LOAD:
                    accumulator = memory[operand];
                    break;
                case STORE:
                    memory[operand] = accumulator;
                    break;
                case ADD:
                    accumulator += memory[operand];
                    break;
                case SUBTRACT:
                    accumulator -= memory[operand];
                    break;
                case DIVIDE:
                    accumulator /= memory[operand];
                    break;
                case MULITPLY:
                    accumulator *= memory[operand];
                    break;
                case MOD:  // Nueva instrucción: Resto
                    accumulator %= memory[operand];
                    break;
                case EXPONENTIATION:  // Nueva instrucción: Exponenciación
                    accumulator = (int) Math.pow(accumulator, memory[operand]);
                    break;
                case BRANCH:
                    instructionCounter = operand - 1;
                    break;
                case BRANCHNEG:
                    if (accumulator < 0) instructionCounter = operand - 1;
                    break;
                case BRANCHZERO:
                    if (accumulator == 0) instructionCounter = operand - 1;
                    break;
                case HALT:
                    run = false;
                    System.out.println("*** Simpletron execution terminated ***");
                    break;
                default:
                    System.out.println("Invalid operation code: " + operationCode);
                    run = false;
                    break;
            }
            instructionCounter++;
        }

        // Imprimir en formato hexadecimal al final
        System.out.println("*** Memory Dump (Hexadecimal Format) ***");
        for (int tens = 0; tens < 1000; tens += 10) {
            System.out.printf("%02X	", tens);
            for (int ones = 0; ones < 10; ones++) {
                System.out.printf("%04X	", memory[tens + ones]);
            }
            System.out.println();
        }
    }
}
