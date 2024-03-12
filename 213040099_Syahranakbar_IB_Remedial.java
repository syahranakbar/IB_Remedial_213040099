package tugas;

import java.util.*;

public class PengirimanBarang {

    static class Graph {
        private Map<String, Map<String, Integer>> graph = new HashMap<>();

        public void tambahRute(String kotaAsal, String kotaTujuan, int jarak) {
            graph.putIfAbsent(kotaAsal, new HashMap<>());
            graph.get(kotaAsal).put(kotaTujuan, jarak);
        }

        public Map<String, Map<String, Integer>> getGraph() {
            return graph;
        }
    }

    public static List<String> cariRuteTerpendek(Map<String, Map<String, Integer>> graph, String kotaAsal, String kotaTujuan) {
        if (!graph.containsKey(kotaAsal) || !graph.containsKey(kotaTujuan)) {
            return null; // Tidak ada rute jika kota asal atau tujuan tidak ditemukan
        }

        Map<String, Integer> jarakMin = new HashMap<>();
        Map<String, String> kotaSebelumnya = new HashMap<>();
        Set<String> belumDikunjungi = new HashSet<>();

        for (String kota : graph.keySet()) {
            jarakMin.put(kota, Integer.MAX_VALUE);
            kotaSebelumnya.put(kota, null);
            belumDikunjungi.add(kota);
        }

        jarakMin.put(kotaAsal, 0);

        while (!belumDikunjungi.isEmpty()) {
            String kotaSekarang = null;
            for (String kota : belumDikunjungi) {
                if (kotaSekarang == null || jarakMin.get(kota) < jarakMin.get(kotaSekarang)) {
                    kotaSekarang = kota;
                }
            }

            if (kotaSekarang.equals(kotaTujuan)) {
                List<String> ruteTerpendek = new ArrayList<>();
                while (kotaSekarang != null) {
                    ruteTerpendek.add(kotaSekarang);
                    kotaSekarang = kotaSebelumnya.get(kotaSekarang);
                }
                Collections.reverse(ruteTerpendek);
                return ruteTerpendek;
            }

            belumDikunjungi.remove(kotaSekarang);

            for (String tetangga : graph.get(kotaSekarang).keySet()) {
                int jarakBaru = jarakMin.get(kotaSekarang) + graph.get(kotaSekarang).get(tetangga);
                if (jarakBaru < jarakMin.get(tetangga)) {
                    jarakMin.put(tetangga, jarakBaru);
                    kotaSebelumnya.put(tetangga, kotaSekarang);
                }
            }
        }

        return null; // Tidak ada rute yang ditemukan
    }

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.tambahRute("Kota A", "Kota B", 10);
        graph.tambahRute("Kota A", "Kota C", 15);
        graph.tambahRute("Kota B", "Kota D", 12);
        graph.tambahRute("Kota C", "Kota D", 10);
        graph.tambahRute("Kota D", "Kota E", 5);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Kota Asal: ");
        String kotaAsal = scanner.nextLine();
        System.out.print("Kota Tujuan: ");
        String kotaTujuan = scanner.nextLine();

        List<String> ruteTerpendek = cariRuteTerpendek(graph.getGraph(), kotaAsal, kotaTujuan);

        if (ruteTerpendek != null) {
            System.out.println("Rute terpendek dari " + kotaAsal + " ke " + kotaTujuan + " adalah: " + ruteTerpendek);
        } else {
            System.out.println("Tidak ada rute yang tersedia dari " + kotaAsal + " ke " + kotaTujuan + ".");
        }
    }
}