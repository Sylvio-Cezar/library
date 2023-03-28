import java.util.Scanner;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

public class LibraryMain {
    // Variáveis globais
    private static final int MAX_BOOKS = 1000;
    private static final String[] titles = new String[MAX_BOOKS];
    private static final int[] pages = new int[MAX_BOOKS];
    private static final String[] authors = new String[MAX_BOOKS];
    private static final String[] areas = new String[MAX_BOOKS];
    private static int actualBookNumber = 0;

    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            TimeUnit.MILLISECONDS.sleep(1500);
            buildMenu();
            int option = scan.nextInt();

            switch (option) {
                case 1 -> addBook(scan);
                case 2 -> removeBook(scan);
                case 3 -> searchBook(scan);
                case 4 -> generateBookReport();
                case 5 -> {
                    System.out.println("Adeus!");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void buildMenu() {
        System.out.println("\n----- Biblioteca -----");
        System.out.println("Selecione a opção desejada:");
        System.out.println("1 - Adicionar Livro");
        System.out.println("2 - Remover Livro");
        System.out.println("3 - Buscar Livro");
        System.out.println("4 - Gerar Relatório");
        System.out.println("5 - Sair");
        System.out.print("Digite: ");
    }

    private static void addBook(Scanner scan) throws InterruptedException {
        if (actualBookNumber >= MAX_BOOKS) {
            System.out.println("\nA biblioteca está cheia, exclua algum livro e tente novamente!");
            TimeUnit.MILLISECONDS.sleep(1500);
            return;
        }

        System.out.print("Digite o título do livro: ");
        scan.nextLine();
        String title = scan.nextLine();

        System.out.print("Digite o número de páginas: ");
        int page = scan.nextInt();
        scan.nextLine();

        System.out.print("Digite o nome do autor: ");
        String author = scan.nextLine();

        System.out.print("Digite a área de interesse: ");
        String area = scan.nextLine();

        titles[actualBookNumber] = title;
        pages[actualBookNumber] = page;
        authors[actualBookNumber] = author;
        areas[actualBookNumber] = area;
        actualBookNumber++;

        System.out.println("\nLivro adicionado com sucesso!");
        TimeUnit.MILLISECONDS.sleep(1500);
    }

    private static void removeBook(Scanner scan) throws InterruptedException {
        System.out.print("Digite o título do livro a ser removido: ");
        String title = scan.nextLine();

        int index = -1;
        for (int i = 0; i < actualBookNumber; i++) {
            if (titles[i].equalsIgnoreCase(title)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Livro não encontrado!");
            return;
        }

        for (int i = index; i < actualBookNumber - 1; i++) {
            titles[i] = titles[i + 1];
            pages[i] = pages[i + 1];
            authors[i] = authors[i + 1];
            areas[i] = areas[i + 1];
        }
        actualBookNumber--;

        System.out.println("\nLivro removido com sucesso!");
        TimeUnit.MILLISECONDS.sleep(1500);
    }

    private static void searchBook(Scanner scan) throws InterruptedException {
        buildSearchMenu();
        int option = scan.nextInt();
        scan.nextLine();

        switch (option) {
            case 1 -> {
                System.out.print("Digite o título do livro: ");
                String title = scan.nextLine();
                findBook(title, "title");
            }
            case 2 -> {
                System.out.print("Digite o nome do autor: ");
                String author = scan.nextLine();
                findBook(author, "author");
            }
            case 3 -> {
                System.out.print("Digite a área de interesse: ");
                String area = scan.nextLine();
                findBook(area, "area");
            }
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void buildSearchMenu() {
        System.out.println("\n----- Opções de Busca -----");
        System.out.println("1 - Título");
        System.out.println("2 - Autor");
        System.out.println("3 - Área de interesse");
        System.out.print("Digite a opção de busca: ");
    }

    private static void findBook(String term, String type) throws InterruptedException {
        String[] param = switch (type) {
            case "title" -> titles;
            case "author" -> authors;
            case "area" -> areas;
            default -> new String[MAX_BOOKS];
        };
        boolean found = false;
        for (int i = 0; i < actualBookNumber; i++) {
            if (param[i].equalsIgnoreCase(term)) {
                System.out.printf("\n%s, %d páginas, %s, %s\n", titles[i], pages[i], authors[i], areas[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("\nLivro não encontrado!");
        }
        TimeUnit.MILLISECONDS.sleep(1500);
    }

    public static void generateBookReport() {
        try {
            FileWriter csvWriter = new FileWriter("books.csv");
            csvWriter.append("nome_do_livro,numero_de_paginas,nome_do_autor,area_de_interesse\n");

            for (int i = 0; i < actualBookNumber; i++) {
                csvWriter.append(titles[i]).append(",");
                csvWriter.append(String.valueOf(pages[i])).append(",");
                csvWriter.append(authors[i]).append(",");
                csvWriter.append(areas[i]).append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
