/**
 * @author Hussein Abdallah
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindConnections {
    public static void main(String[] args) {
        int count = 0;
        int h = 72;
        int w = 75;
        char image[][] = new char[h][w];
        try {
            File file = new File(args[0]);
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine();
                line = line.replaceAll("\\+","1");
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '1') image[count][i] = '1';
                }
                count++;
                System.out.println(line);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("Could not read file");
            e.printStackTrace();
        }

        System.out.println("-------------------------------------------------------------");

        count = 0;
        DisjointSet set = new DisjointSet(w*h+1);
        set.uandf(w*h+1);
        try {
            File file = new File(args[0]);
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '+') {
                        set.make_set((count * w + i) + 1);
                        if (i > 0 && image[count][i-1] != 0) {
                            set.union_sets(count*w+i, count*w+i+1);
                        }
                        if (count > 0 && image[count-1][i] != 0) {
                            set.union_sets((count-1)*w+i+1, count*w+i+1);
                        }
                        if (count > 0 && i > 0 && image[count-1][i-1] != 0) {
                            set.union_sets((count-1)*w+i, count*w+i+1);
                        }
                        if (count > 0 && i < w-1 && image[count-1][i+1] != 0) {
                            set.union_sets((count-1)*w+i+2, count*w+i+1);
                        }
                    }
                }
                count++;
                //System.out.println(line);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("Could not read file");
            e.printStackTrace();
        }
        List<Character> identifiers = new ArrayList<Character>();
        List<Integer> parents = new ArrayList<Integer>();
        List<Integer> size = new ArrayList<Integer>();

        char c = 'a'-1;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (image[y][x] != 0) {
                    int p = set.find_set((y * w + x) + 1);
                    if (parents.contains(p)) {
                        image[y][x] = identifiers.get(parents.indexOf(p));
                        size.set(parents.indexOf(p), size.get(parents.indexOf(p))+1);
                    } else {
                        c++;
                        image[y][x] = c;
                        parents.add(p);
                        identifiers.add(c);
                        size.add(1);
                    }
                    System.out.print(image[y][x]);
                } else System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------");
        for (int i = 0; i < identifiers.size(); i++) {
            System.out.println("Label: "+identifiers.get(i) + "    Size: "+ size.get(i));
        }

        System.out.println("-------------------------------------------------------------");
        /*for (int i = 0; i < identifiers.size(); i++) {
            if (size.get(i) > 1) System.out.println("Label: "+identifiers.get(i) + "    Size: "+ size.get(i));
        }*/
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (image[y][x] != 0) {
                    int p = set.find_set((y * w + x) + 1);
                    if (size.get(parents.indexOf(p)) > 1)
                        System.out.print(identifiers.get(parents.indexOf(p)));
                    else System.out.print(" ");
                } else System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------------------");
        /*for (int i = 0; i < identifiers.size(); i++) {
            if (size.get(i) > 11) System.out.println("Label: "+identifiers.get(i) + "    Size: "+ size.get(i));
        }*/
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (image[y][x] != 0) {
                    int p = set.find_set((y * w + x) + 1);
                    if (size.get(parents.indexOf(p)) > 11)
                        System.out.print(identifiers.get(parents.indexOf(p)));
                    else System.out.print(" ");
                } else System.out.print(" ");
            }
            System.out.println();
        }
    }
}