public class Main {

    public static void main(String[] args) {

        // ---------- Caso 1: caminho direto, sem tesouros ----------
        char[][] caso1 = Labirinto.criarLabirinto(
                "S . . # # #\n" +
                "# # . # # #\n" +
                "# # . . # #\n" +
                "# # # . # #\n" +
                "# # # . . E\n" +
                "# # # # # #");

        System.out.println("=== Caso 1: caminho direto para a saída ===");
        Labirinto.mostrarLabirinto(caso1);
        Labirinto.acharCaminho(caso1);

        // ---------- Caso 2: varios tesouros, escolha gulosa ----------
        char[][] caso2 = Labirinto.criarLabirinto(
                "S . . # . .\n" +
                "# # . # . #\n" +
                ". . . . T .\n" +
                ". # # # . #\n" +
                ". . T . . #\n" +
                "E # # # # #");

        System.out.println();
        System.out.println("=== Caso 2: múltiplos tesouros (guloso) ===");
        Labirinto.mostrarLabirinto(caso2);
        Labirinto.acharCaminho(caso2);

        // ---------- Caso 3: sem saida ----------
        char[][] caso3 = Labirinto.criarLabirinto(
                "S . . # . .\n" +
                "# # . # . #\n" +
                ". . . # T .\n" +
                ". # # # . #\n" +
                ". . T # . E\n" +
                "# # # # # #");

        System.out.println();
        System.out.println("=== Caso 3: sem saída possível ===");
        Labirinto.mostrarLabirinto(caso3);
        Labirinto.acharCaminho(caso3);

        // ---------- Labirinto do enunciado ----------
        char[][] enunciado = Labirinto.criarLabirinto(
                "S . . # . .\n" +
                "# # . # . #\n" +
                ". . . . T .\n" +
                ". # # # . #\n" +
                ". . T . . E\n" +
                "# # # # # #");

        System.out.println();
        System.out.println("=== Labirinto do enunciado (teste de mesa) ===");
        Labirinto.mostrarLabirinto(enunciado);

        if (args.length > 0 && args[0].equals("debug")) {
            Labirinto.debug = true;
        }
        Labirinto.acharCaminho(enunciado);
    }
}
