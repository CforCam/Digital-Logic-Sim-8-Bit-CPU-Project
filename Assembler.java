import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Assembler {

    public static HashMap<String, String> machineCode = new HashMap();

    public static void main(String[] args) {
        while(true) {
            // Get file from user
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Insert file path (q to quit): ");
            String filePath = keyboard.nextLine();

            if (filePath.equals("q")) {
                keyboard.close();
                System.exit(0);
            }

            Scanner fileScanner;
            try {
                fileScanner = new Scanner(new File(filePath));
            } catch (FileNotFoundException e) {
                System.out.println("FILE NOT FOUND");
                continue;
            }

            initMachineCode();
            int assembledCode = Assemble(fileScanner);
            if(assembledCode == -1) {
                System.out.println("Program assembled!");
            }
            else {
                System.out.println("Line: " + assembledCode + ", Error in assembling");
            }
            fileScanner.close();
        }
    }

    public static int Assemble(Scanner fileInput) {
        String output = "";
        int lineNum = 0;
        while(fileInput.hasNextLine()) {
        
            String line = fileInput.nextLine().trim();
            lineNum++;
            String hex = "";
            
            // Line is a comment or white space
            if(line.startsWith("#") || line.replace("\n", "").isEmpty()) {
               continue;
            }
            
            // Not a jump
            if(line.contains(",")) {
                // If last input is a number
                if(line.split(",")[1].trim().matches("\\d+")) {
                    hex = machineCode.get(line.split(",")[0].concat(", *PC"));
                    output += hex + "\n" + line.split(", ")[1] + "\n";
                    continue;
                }
            }
            hex = machineCode.get(line);
            if(hex != null) {
                output += hex + "\n";
                continue;
            }
            // Error line
            return lineNum;
        }
        System.out.println(output);
        return -1;
    }

    public static void initMachineCode() {
        machineCode.put("LOAD A, *A","2");
        machineCode.put("LOAD B, *A","4");
        machineCode.put("LOAD A, *PC","6");
        machineCode.put("LOAD B, *PC","8");
        machineCode.put("STORE *A, A","A");
        machineCode.put("STORE *A, B","C");
        machineCode.put("STORE A, B","E");
        machineCode.put("STORE B, A","F");
        machineCode.put("STORE JR, A","10");
        machineCode.put("STORE JR, B","11");
        machineCode.put("ADD A, B","12");
        machineCode.put("ADD B, A","13");
        machineCode.put("SUB A, B","14");
        machineCode.put("SUB B, A","15");
        machineCode.put("AND A, B","16");
        machineCode.put("AND B, A","17");
        machineCode.put("OR A, B","18");
        machineCode.put("OR B, A","19");
        machineCode.put("XOR A, B","1A");
        machineCode.put("XOR B, A","1B");
        machineCode.put("INV A","1C");
        machineCode.put("INV B","1D");
        machineCode.put("JMP","1E");
        machineCode.put("JLT","1F");
        machineCode.put("JLE","20");
        machineCode.put("JEQ","21");
        machineCode.put("JGE","22");
        machineCode.put("JGT","23");

    }
}
