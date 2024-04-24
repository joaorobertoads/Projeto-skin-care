import java.util.*;

public class Main {
    enum Categoria {
        FACIAL,
        CORPORAL
    }

    enum TipoPele {
        OLEOSA,
        SECA,
        MISTA,
        ACNEICA
    }

    enum Produto {
        SABONETE_LIQUIDO_FACIAL("Limpeza suave e controle da oleosidade"),
        HIDRATANTE_FACIAL("Hidratação intensa e proteção contra ressecamento"),
        OLEO_HIDRATANTE_FACIAL("Nutrição intensa e revitalização da pele"),
        PROTETOR_SOLAR_FACIAL("Proteção contra raios UV e prevenção de manchas"),
        ESFOLIANTE_FACIAL("Remoção de células mortas e renovação da pele"),
        ACIDO_FACIAL("Tratamento de acne e renovação celular"),
        VITAMINA_C_FACIAL("Estímulo de colágeno e combate aos radicais livres"),
        DEMAQUILANTE_FACIAL("Remoção eficaz de maquiagem e impurezas"),
        SABONETE_LIQUIDO_CORPORAL("Higienização suave e nutrição da pele"),
        HIDRATANTE_CORPORAL("Hidratação profunda e restauração da barreira cutânea"),
        PROTETOR_SOLAR_CORPORAL("Proteção UV e prevenção do envelhecimento precoce"),
        VITAMINA_C_CORPORAL("Hidratação antioxidante e uniformização do tom da pele"),
        LOCAO_HIDRATANTE_CORPORAL("Hidratação prolongada e revitalização da pele"),
        OLEO_HIDRATANTE_CORPORAL("Nutrição intensa e maciez da pele");

        private final String descricao;

        Produto(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    static class Cliente {
        Categoria categoria;
        TipoPele tipoPele;
        boolean usaProtetor;
        boolean possuiAlergia;
        boolean rotinasCuidados;
        boolean casosMelasma;
        boolean contraindicacao;

        public Cliente(Categoria categoria, TipoPele tipoPele, boolean usaProtetor, boolean possuiAlergia, boolean rotinasCuidados, boolean casosMelasma, boolean contraindicacao) {
            this.categoria = categoria;
            this.tipoPele = tipoPele;
            this.usaProtetor = usaProtetor;
            this.possuiAlergia = possuiAlergia;
            this.rotinasCuidados = rotinasCuidados;
            this.casosMelasma = casosMelasma;
            this.contraindicacao = contraindicacao;
        }
    }

    static class ProdutoRecomendado {
        Produto produto;
        String descricao;

        public ProdutoRecomendado(Produto produto, String descricao) {
            this.produto = produto;
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return produto + " - " + descricao;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Cliente cliente = obterInformacoesCliente(scanner);

        List<ProdutoRecomendado> recomendacoesUsuario = recomendarProdutos(cliente);
        System.out.println("Produtos recomendados:");
        for (ProdutoRecomendado produto : recomendacoesUsuario) {
            System.out.println(produto);
        }

        scanner.close();
    }

    public static Cliente obterInformacoesCliente(Scanner scanner) {
        System.out.println("Sua maior preocupação é a pele do rosto ou corporal?\n(r para Rosto, c para Corporal)");
        char preocupacao = obterRespostaExclusiva(scanner, 'r', 'c');
        preocupacao = Character.toLowerCase(preocupacao);
        Categoria categoriaRecomendada = preocupacao == 'r' ? Categoria.FACIAL : Categoria.CORPORAL;
        
        if(preocupacao == 'c') {
             System.out.println("Qual o seu tipo de pele?\n(s para Seca, a para Acneica)");
        } else {
            System.out.println("Qual o seu tipo de pele?\n(o para Oleosa, s para Seca, m para Mista, a para Acneica)"); 
        }

        TipoPele tipoPele = obterTipoPele(scanner);
        
        System.out.println("Você usa protetor solar com frequência?\n(s para Sim, n para Não)");
        char usaProtetor = obterRespostaExclusiva(scanner, 's', 'n');
        usaProtetor = Character.toLowerCase(usaProtetor);

        System.out.println("Possui alguma alergia?\n(s para Sim, n para Não)");
        char possuiAlergia = obterRespostaExclusiva(scanner, 's', 'n');
        possuiAlergia = Character.toLowerCase(possuiAlergia);
        String alergia = "";
        if (possuiAlergia == 's') {
            System.out.println("Informe a alergia:");
            alergia = scanner.nextLine();
        }

        char contraindicacao = 'n';
        contraindicacao = Character.toLowerCase(contraindicacao);
        if (categoriaRecomendada == Categoria.FACIAL && possuiAlergia == 'n') {
            System.out.println("Possui alguma contraindicação?\n(s para Sim, n para Não)");
            contraindicacao = obterRespostaExclusiva(scanner, 's', 'n');
        }

        System.out.println("Tem rotinas de cuidados com a pele com frequência?\n(s para Sim, n para Não)");
        char rotinasCuidados = obterRespostaExclusiva(scanner, 's', 'n');
        rotinasCuidados = Character.toLowerCase(rotinasCuidados);

        System.out.println("Tem casos de melasma na família?\n(s para Sim, n para Não)");
        char casosMelasma = obterRespostaExclusiva(scanner, 's', 'n');
        casosMelasma = Character.toLowerCase(casosMelasma);

        return new Cliente(categoriaRecomendada, tipoPele, usaProtetor == 'n', possuiAlergia == 's', rotinasCuidados == 'n', casosMelasma == 's', contraindicacao == 's');
    }

    public static TipoPele obterTipoPele(Scanner scanner) {
        TipoPele tipoPele = null;
        while (tipoPele == null) {
            char tipo = Character.toLowerCase(scanner.nextLine().charAt(0));
            tipo = Character.toLowerCase(tipo);
            switch (tipo) {
                case 'o':
                    tipoPele = TipoPele.OLEOSA;
                    break;
                case 's':
                    tipoPele = TipoPele.SECA;
                    break;
                case 'm':
                    tipoPele = TipoPele.MISTA;
                    break;
                case 'a':
                    tipoPele = TipoPele.ACNEICA;
                    break;
                default:
                    System.out.println("Tipo de pele inválido. Tente novamente.");
            }
        }
        return tipoPele;
    }

    public static char obterRespostaExclusiva(Scanner scanner, char... opcoes) {
        char resposta;
        Set<Character> opcoesSet = new HashSet<>();
        for (char opcao : opcoes) {
            opcoesSet.add(opcao);
        }
        while (true) {
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                resposta = Character.toLowerCase(input.charAt(0));
            if (opcoesSet.contains(resposta)) {
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
        return resposta;
    }


    public static List<ProdutoRecomendado> recomendarProdutos(Cliente cliente) {
        List<ProdutoRecomendado> recomendacoesUsuario = new ArrayList<>();

        if (cliente.categoria == Categoria.FACIAL) {
            if (cliente.tipoPele == TipoPele.OLEOSA || cliente.tipoPele == TipoPele.MISTA) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.SABONETE_LIQUIDO_FACIAL, "Limpeza suave e controle da oleosidade"));

            } else if (cliente.tipoPele == TipoPele.SECA) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.HIDRATANTE_FACIAL, "Hidratação intensa e proteção contra ressecamento"));
            } else if (cliente.tipoPele == TipoPele.ACNEICA) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.SABONETE_LIQUIDO_FACIAL, "Limpeza suave e controle da oleosidade"));
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.ACIDO_FACIAL, "Tratamento de acne e renovação celular"));
            } if (cliente.usaProtetor) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.PROTETOR_SOLAR_FACIAL, "Proteção contra raios UV e prevenção de manchas"));
            } if (cliente.possuiAlergia) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.DEMAQUILANTE_FACIAL, "Remoção eficaz de maquiagem e impurezas"));
            } if (cliente.casosMelasma) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.VITAMINA_C_FACIAL, "Hidratação antioxidante e uniformização do tom da pele"));
            } if (cliente.contraindicacao) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.OLEO_HIDRATANTE_FACIAL, "Nutrição intensa e revitalização da pele"));
            }
        } else if (cliente.categoria == Categoria.CORPORAL) {
            if (cliente.tipoPele == TipoPele.SECA) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.OLEO_HIDRATANTE_CORPORAL, "Nutrição intensa e maciez da pele"));
            } else if (cliente.tipoPele == TipoPele.ACNEICA) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.SABONETE_LIQUIDO_FACIAL, "Limpeza suave e controle da oleosidade"));
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.VITAMINA_C_CORPORAL, "Hidratação antioxidante e uniformização do tom da pele"));
            }
            if (cliente.usaProtetor) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.PROTETOR_SOLAR_CORPORAL, "Proteção UV e prevenção do envelhecimento precoce"));
            }
            if (cliente.possuiAlergia) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.HIDRATANTE_CORPORAL, "Hidratação profunda e restauração da barreira cutânea"));
            }
            if (cliente.rotinasCuidados) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.LOCAO_HIDRATANTE_CORPORAL, "Hidratação profunda e revitalização da pele"));
            }
            if (cliente.casosMelasma) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.VITAMINA_C_CORPORAL, "Hidratação antioxidante e uniformização do tom da pele"));
            }
            if (cliente.contraindicacao) {
                recomendacoesUsuario.add(new ProdutoRecomendado(Produto.OLEO_HIDRATANTE_CORPORAL, "Nutrição intensa e maciez da pele"));
            }
        }

        if (recomendacoesUsuario.size() > 3) {
            recomendacoesUsuario = recomendacoesUsuario.subList(0, 3);
        }
        return recomendacoesUsuario;
    }
}
