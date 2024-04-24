import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Integer> historicoCalculos = new HashMap<>();
    private static final int IDADE_MINIMA = 0;
    private static final int IDADE_MAXIMA = 120;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    calcularFPS(scanner);
                    break;
                case 2:
                    exibirHistorico();
                    break;
                case 3:
                    continuar = false;
                    System.out.println("Obrigado por usar a Calculadora de Proteção Solar da SOS SkinCare!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }

        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Calcular FPS recomendado");
        System.out.println("2. Exibir histórico de cálculos");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public static void calcularFPS(Scanner scanner) {
        System.out.println("\nInforme seu nome: ");
        String nome = scanner.nextLine();

        int idade;
        do {
            System.out.println("Informe sua idade: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, informe uma idade válida.");
                scanner.next();
            }
            idade = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner
        } while (idade < IDADE_MINIMA || idade > IDADE_MAXIMA);

        System.out.println("Informe sua localização geográfica (ex: cidade, país): ");
        String localizacao = scanner.nextLine();

        String tipoPele;
        do {
            System.out.println("Informe seu tipo de pele (claro, médio, escuro): ");
            tipoPele = scanner.nextLine().toLowerCase();
            if (!tipoPele.equals("claro") && !tipoPele.equals("médio") && !tipoPele.equals("escuro")) {
                System.out.println("Por favor, informe um tipo de pele válido.");
            }
        } while (!tipoPele.equals("claro") && !tipoPele.equals("médio") && !tipoPele.equals("escuro"));

        String atividade;
        do {
            System.out.println("Informe a atividade ao ar livre (leve, moderada, intensa): ");
            atividade = scanner.nextLine().toLowerCase();
            if (!atividade.equals("leve") && !atividade.equals("moderada") && !atividade.equals("intensa")) {
                System.out.println("Por favor, informe uma atividade válida.");
            }
        } while (!atividade.equals("leve") && !atividade.equals("moderada") && !atividade.equals("intensa"));

        double duracaoExposicaoHoras;
        do {
            System.out.println("Informe a duração estimada da exposição ao sol (em horas): ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Por favor, informe um valor numérico válido para a duração.");
                scanner.next();
            }
            duracaoExposicaoHoras = scanner.nextDouble();
            scanner.nextLine(); // Limpar o buffer do scanner
        } while (duracaoExposicaoHoras <= 0);

        boolean medicamentoFotossensibilizante;
        do {
            System.out.println("Você está utilizando algum medicamento fotossensibilizante? (sim/não): ");
            String respostaMedicamento = scanner.nextLine().toLowerCase();
            medicamentoFotossensibilizante = respostaMedicamento.equals("sim") || respostaMedicamento.equals("não");
            if (!medicamentoFotossensibilizante) {
                System.out.println("Por favor, responda com 'sim' ou 'não'.");
            }
        } while (!medicamentoFotossensibilizante);

        boolean teveQueimaduras;
        do {
            System.out.println("Já teve queimaduras solares no passado? (sim/não): ");
            String respostaQueimaduras = scanner.nextLine().toLowerCase();
            teveQueimaduras = respostaQueimaduras.equals("sim") || respostaQueimaduras.equals("não");
            if (!teveQueimaduras) {
                System.out.println("Por favor, responda com 'sim' ou 'não'.");
            }
        } while (!teveQueimaduras);

        boolean maquiagemComFPS;
        do {
            System.out.println("Você utiliza maquiagem com FPS regularmente? (sim/não): ");
            String respostaMaquiagem = scanner.nextLine().toLowerCase();
            maquiagemComFPS = respostaMaquiagem.equals("sim") || respostaMaquiagem.equals("não");
            if (!maquiagemComFPS) {
                System.out.println("Por favor, responda com 'sim' ou 'não'.");
            }
        } while (!maquiagemComFPS);

        int fpsRecomendado = calcularFPSRecomendado(nome, idade, localizacao, tipoPele, atividade, duracaoExposicaoHoras,
                medicamentoFotossensibilizante, teveQueimaduras, maquiagemComFPS);

        System.out.println("\nFPS recomendado pela SOS SkinCare: " + fpsRecomendado);

        // Adicionar o cálculo ao histórico
        adicionarAoHistorico(nome, idade, localizacao, tipoPele, atividade, duracaoExposicaoHoras, fpsRecomendado);
    }

    public static int calcularFPSRecomendado(String nome, int idade, String localizacao, String tipoPele, String atividade,
            double duracaoExposicaoHoras, boolean medicamentoFotossensibilizante,
            boolean teveQueimaduras, boolean maquiagemComFPS) {
        // Definição do FPS base
        int fpsBase = 30;

        // Fatores de ajuste para calcular o FPS recomendado
        double fatorTipoPele = 1.0;
        double fatorAtividade = 1.0;
        double fatorLocalizacao = 1.0;
        double fatorDuracaoExposicao = 1.0;

        // Ajuste do fator com base no tipo de pele
        if (tipoPele.equals("claro")) {
            fatorTipoPele = 1.5;
        } else if (tipoPele.equals("escuro")) {
            fatorTipoPele = 0.5;
        }

        // Ajuste do fator com base na atividade ao ar livre
        if (atividade.equals("moderada")) {
            fatorAtividade = 1.0;
        } else if (atividade.equals("intensa")) {
            fatorAtividade = 1.25;
        }

        // Ajuste do fator com base na localização geográfica
        if (localizacao.equals("praia")) {
            fatorLocalizacao = 1.25;
        } else if (localizacao.equals("alta montanha")) {
            fatorLocalizacao = 1.5;
        }

        // Ajuste do fator com base na duração estimada da exposição solar
        if (duracaoExposicaoHoras > 3.0) {
            fatorDuracaoExposicao = 1.25;
        }

        // Cálculo do FPS recomendado
        double fpsRecomendadoDouble = fpsBase * fatorTipoPele * fatorAtividade * fatorLocalizacao * fatorDuracaoExposicao;
        int fpsRecomendado = (int) Math.round(fpsRecomendadoDouble);

        return fpsRecomendado;
    }

    public static void adicionarAoHistorico(String nome, int idade, String localizacao, String tipoPele, String atividade, 
            double duracaoExposicaoHoras, int fps) {
        String historico = String.format("Nome: %s\nIdade: %d\nLocalização: %s\nTipo de pele: %s\nAtividade: %s\nDuração exposição (horas): %.2f\nFPS: %d",
                nome, idade, localizacao, tipoPele, atividade, duracaoExposicaoHoras, fps);
        historicoCalculos.put(historico, fps);
        System.out.println("Adicionado ao histórico:\n" + historico);
    }

    public static void exibirHistorico() {
        if (historicoCalculos.isEmpty()) {
            System.out.println("Histórico de cálculos está vazio.");
        } else {
            System.out.println("\nHistórico de cálculos da SOS SkinCare:");
            for (String registro : historicoCalculos.keySet()) {
                System.out.println(registro + "\n");
            }
        }
    }
}
