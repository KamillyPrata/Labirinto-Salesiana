import java.util.ArrayList;
import java.util.List;

public class Labirinto {

    
    private static final int[] DL = {-1, 0, 1, 0}; 
    private static final int[] DC = {0, 1, 0, -1}; 

    private static List<int[]> melhorCaminho;

    public static boolean debug = false;
    private static int contadorChamadas = 0;

    public static void acharCaminho(char[][] labirinto) {
        contadorChamadas = 0; 
        int[] inicio = procurar(labirinto, 'S');
        int[] saida = procurar(labirinto, 'E');

        
        if (inicio == null || saida == null) {
            System.out.println("Não há saída possível!");
            return;
        }

        List<int[]> caminhoDireto = caminhoMaisRapido(labirinto, inicio, saida);
        if (caminhoDireto == null) {
            System.out.println("Não há saída possível!");
            return;
        }

    
        List<int[]> tesouros = listarTesouros(labirinto);
        boolean[] coletado = new boolean[tesouros.size()];
        int totalColetado = 0;

       
        List<String> passos = new ArrayList<String>();
        passos.add("S");

        int[] atual = inicio;

        while (true) {
            
            int escolhido = -1;
            List<int[]> melhorTrecho = null;

            for (int i = 0; i < tesouros.size(); i++) {
                if (coletado[i]) {
                    continue; 
                }
                List<int[]> trecho = caminhoMaisRapido(labirinto, atual, tesouros.get(i));
                if (trecho == null) {
                    continue; 
                }
               
                if (melhorTrecho == null || trecho.size() < melhorTrecho.size()) {
                    melhorTrecho = trecho;
                    escolhido = i;
                }
            }

            if (escolhido == -1) {
                break; 
            }

            if (debug) {
                System.out.println("[guloso] tesouro mais perto: (" + tesouros.get(escolhido)[0] + ","
                        + tesouros.get(escolhido)[1] + ") a " + (melhorTrecho.size() - 1) + " passos");
            }

            
            adicionarTrecho(passos, melhorTrecho, "T");
            coletado[escolhido] = true;
            totalColetado++;
            atual = tesouros.get(escolhido);
        }


        List<int[]> trechoFinal = caminhoMaisRapido(labirinto, atual, saida);
        adicionarTrecho(passos, trechoFinal, "E");

       
        StringBuilder sb = new StringBuilder("Caminho percorrido: ");
        for (int i = 0; i < passos.size(); i++) {
            if (i > 0) {
                sb.append(" → ");
            }
            sb.append(passos.get(i));
        }
        System.out.println(sb.toString());
        System.out.println("Tesouros coletados: " + totalColetado);
    }





   
    public static List<int[]> caminhoMaisRapido(char[][] lab, int[] origem, int[] destino) {
        melhorCaminho = null;
        boolean[][] visitado = new boolean[lab.length][lab[0].length];
        List<int[]> caminhoAtual = new ArrayList<int[]>();
        explorar(lab, origem[0], origem[1], destino, visitado, caminhoAtual, 0);
        return melhorCaminho;
    }

   




    private static void explorar(char[][] lab, int l, int c, int[] destino,
                                 boolean[][] visitado, List<int[]> caminhoAtual, int nivel) {
        contadorChamadas++;
        if (debug) {
            System.out.println(espacos(nivel) + "chamada #" + contadorChamadas
                    + " explorar(" + l + "," + c + ")");
        }

       
        visitado[l][c] = true;
        caminhoAtual.add(new int[]{l, c});

        if (l == destino[0] && c == destino[1]) {
            if (melhorCaminho == null || caminhoAtual.size() < melhorCaminho.size()) {
                melhorCaminho = new ArrayList<int[]>(caminhoAtual);
            }
        } else if (melhorCaminho == null || caminhoAtual.size() < melhorCaminho.size()) {
            for (int d = 0; d < 4; d++) {
                int nl = l + DL[d];
                int nc = c + DC[d];
                if (podeAndar(lab, nl, nc, destino, visitado)) {
                    explorar(lab, nl, nc, destino, visitado, caminhoAtual, nivel + 1);
                }
            }
        }

        caminhoAtual.remove(caminhoAtual.size() - 1);
        visitado[l][c] = false;
    }

  



    private static boolean podeAndar(char[][] lab, int l, int c, int[] destino, boolean[][] visitado) {
        if (l < 0 || l >= lab.length || c < 0 || c >= lab[0].length) {
            return false;
        }

        if (lab[l][c] == '#') {
            return false;
        }
        
        if (visitado[l][c]) {
            return false;
        }
        
        if (lab[l][c] == 'E' && !(l == destino[0] && c == destino[1])) {
            return false;
        }
        return true;
    }

    
    private static int[] procurar(char[][] lab, char simbolo) {
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j] == simbolo) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    
    private static List<int[]> listarTesouros(char[][] lab) {
        List<int[]> lista = new ArrayList<int[]>();
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                if (lab[i][j] == 'T') {
                    lista.add(new int[]{i, j});
                }
            }
        }
        return lista;
    }

    




    private static void adicionarTrecho(List<String> passos, List<int[]> trecho, String rotuloFinal) {
        for (int i = 1; i < trecho.size(); i++) { // comeca em 1: a casa 0 ja esta no caminho
            if (i == trecho.size() - 1) {
                passos.add(rotuloFinal);
            } else {
                passos.add("(" + trecho.get(i)[0] + "," + trecho.get(i)[1] + ")");
            }
        }
    }

    
    public static char[][] criarLabirinto(String texto) {
        String[] linhas = texto.split("\n");
        char[][] matriz = new char[linhas.length][];
        for (int i = 0; i < linhas.length; i++) {
            String[] simbolos = linhas[i].trim().split(" ");
            matriz[i] = new char[simbolos.length];
            for (int j = 0; j < simbolos.length; j++) {
                matriz[i][j] = simbolos[j].charAt(0);
            }
        }
        return matriz;
    }

    
    public static void mostrarLabirinto(char[][] lab) {
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[i].length; j++) {
                System.out.print(lab[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static String espacos(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}
