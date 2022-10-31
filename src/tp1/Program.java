package tp1;

import java.util.Scanner;

class Program {

    private static final Scanner STDIN = new Scanner(System.in);
    private static final int MAX_DB_SIZE = 2;

    private static String choice = "0";

    private static String[] studentsDb = new String[MAX_DB_SIZE];
    private static float[] av1Db = new float[MAX_DB_SIZE];
    private static float[] av2Db = new float[MAX_DB_SIZE];

    public static void main(String[] args) {

        do {
            showMenu();
            readChoice();
            processChoice();
        } while (!"4".equals(choice));

        System.out.print("[SUCESSO] Programa finalizado.");
        STDIN.close();

    }

    private static void showMenu() {
        System.out.println("\r\n--------------------------------");
        System.out.println("[1] Registrar as notas de um novo aluno");
        System.out.println("[2] Consultar boletim de um aluno.");
        System.out.println("[3] Consultar notas da turma.");
        System.out.println("[4] Sair.");

    }

    private static void readChoice() {
        System.out.print("\r\nSua opcao: ");
        choice = STDIN.next();
    }

    private static void processChoice() {
        if ("1".equals(choice))
            addStudent();
        else if ("2".equals(choice))
            showStudent();
        else if ("3".equals(choice))
            showClassroom();
        else if (!"4".equals(choice))
            System.out.println("[ERRO] Opcao invalida!");

    }

    private static int getNewId() {
        int i = 0;
        while (studentsDb[i] != null) {
            if (i + 1 < MAX_DB_SIZE) {
                i++;
            } else {
                return -1;
            }
        }

        return i;
    }

    private static void addStudent() {
        int id = getNewId();

        String studentName;
        float av1, av2;

        if (id == -1) {
            System.out.println("\r\n[ERRO] Impossivel cadastrar: Maximo de registros alcancado.");
            return;
        }

        do {
            System.out.print("Informe o nome do Aluno: ");
            studentName = STDIN.next();
            System.out.print("Informe a nota AV1 do Aluno: ");
            av1 = STDIN.nextFloat();
            System.out.print("Informe a nota AV2 do Aluno: ");
            av2 = STDIN.nextFloat();
        } while (!saveStudent(id, studentName, av1, av2));

        System.out.println("\r\n[SUCESSO] Cadastro concluido!");
        System.out.println("[INFO] Codigo do cadastro do aluno: " + id);

    }

    private static boolean saveStudent(int id, String studentName, float av1, float av2) {

        if (!validateStudent(av1, av2))
            return false;

        studentsDb[id] = studentName;
        av1Db[id] = av1;
        av2Db[id] = av2;
        return true;
    }

    private static boolean validateStudent(float av1, float av2) {
        if (av1 < 0 || av1 > 10) {
            System.out.println("\r\n[ERRO] Nota AV1 Invalida: Deve ser entre 0 e 10");
            return false;
        } else if (av2 < 0 || av2 > 10) {
            System.out.println("\r\n[ERRO] Nota AV2 Invalida: Deve ser entre 0 e 10");
            return false;
        }

        return true;

    }

    private static void showStudent() {
        System.out.print("Informe o codigo do aluno: ");
        int id = STDIN.nextInt();
        System.out.println("\r\nRELATORIO DO ALUNO");
        if (id >= 0 && id < getNewId())
            printStudent(id);
        else
            System.out.println("\r\n[ERRO] O codigo informado e invalido");
    }

    private static void showClassroom() {
        System.out.println("\r\nRELATORIO DA TURMA");
        for (int id = 0; id < MAX_DB_SIZE; id++) {
            if (studentsDb[id] != null) {
                printStudent(id);
            }
        }

    }

    private static void printStudent(int id) {
        float average = getAvgGrade(av1Db[id], av2Db[id]);
        System.out.println("\r\n - ID: " + id);
        System.out.println(" - Nome: " + studentsDb[id]);
        System.out.println(" - Nota AV1: " + av1Db[id]);
        System.out.println(" - Nota AV2: " + av2Db[id]);
        System.out.println(" - Media: " + average);
        System.out.println(" - Resultado: " + getResult(average));
    }

    private static float getAvgGrade(float av1, float av2) {
        return (av1 + av2) / 2;
    }

    private static String getResult(float average) {
        if (average < 4) {
            return "Reprovado";
        } else if (average >= 4 && average < 7) {
            return "Prova Final";
        } else {
            return "Aprovado";
        }
    }

}