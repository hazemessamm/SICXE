import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
public class SICXE {
        
    public static boolean isNumeric(String str)  
{  
  try  
  {  
    double d = Double.parseDouble(str);  
  }  
  catch(NumberFormatException nfe)  
  {  
    return false;  
  }  
  return true;  
}
   public static String hexToBinary(String hex) {
    int i = Integer.parseInt(hex, 16);
    String bin = Integer.toBinaryString(i);
    return bin;
}
 public static int getDecimal(String hex) {
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        int val = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return val;
    }
    public static String hexToBin(String hex) {
        String bin = "";
        String binFragment = "";
        int iHex;
        hex = hex.trim();
        hex = hex.replaceFirst("0x", "");
        for (int i = 0; i < hex.length(); i++) {
            iHex = Integer.parseInt("" + hex.charAt(i), 16);
            binFragment = Integer.toBinaryString(iHex);
            while (binFragment.length() < 4) {
                binFragment = "0" + binFragment;
            }
            bin += binFragment;
        }
        return bin;
    }
    private static char toHexChar(int hexValue) {
        if (hexValue <= 9 && hexValue >= 0) {
            return (char) (hexValue + '0');
        } else {
            return (char) (hexValue - 10 + 'A');
        }
    }
 public static String decimalToHex(int decimal) {
        String hex = "";
        while (decimal != 0) {
            int hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return hex;
    }
 public static String decToHex(int dec) {
        return Integer.toHexString(dec);
    }
      public static int hexToDec(String hex) {
        return Integer.parseInt(hex,16);
    }
      private static String dectoBin(int dec) {
          return Integer.toBinaryString(dec);
      }
      public static String binaryToHex(String binary) {
        int decimalValue = 0;
        int length = binary.length() - 1;
        for (int i = 0; i < binary.length(); i++) {
            decimalValue += Integer.parseInt(binary.charAt(i) + "") * Math.pow(2, length);
            length--;
        }
        return decimalToHex(decimalValue);
    }
      private static int hexToDecimal(String hex) {
    int decimalValue = 0;
    for (int i = 0; i < hex.length(); i++) {
        char hexChar = hex.charAt(i);
        decimalValue = decimalValue * 16 + hexCharToDecimal(hexChar);
    }
    return decimalValue;
}
      private static int hexCharToDecimal(char character) {
    if (character >= 'A' && character <= 'F')
        return 10 + character - 'A';
    else //character is '0', '1',....,'9'
        return character - '0';
}
        public static void main(String[] args) throws FileNotFoundException {
        Converter.initialize();
        File sicxe = new File("inSICXE.txt");
        if(sicxe.exists())
        {
            System.out.println("File Found");
        }
        else
        {
            System.err.println("File Is Not Found");
        }
        String[] inst = new String[100];
        String[] label = new String[100];
        String[] address = new String[100];
        String[] var = new String[100];
        Scanner scan = new Scanner(sicxe);
        String FL = scan.nextLine();
        String []Split=FL.split(" ");
        String strtAddress=Split[2];
        String progName= Split[0];
        address[0]=strtAddress;
        address[1]=strtAddress;
        Scanner scan2=new Scanner(sicxe);
        String formatType=null;
        int i=0;
        int j,result,Line,prevRes=0;
    while(scan2.hasNext()){
        String progLines= scan2.nextLine();
        String []Split2=progLines.split(" ");
            switch (Split2.length) {
                case 3:
                    label[i] = Split2[0];
                    inst[i] = Split2[1];
                    var[i] = Split2[2];
                    break;
                case 2:
                    label[i] = ";";
                    inst[i] = Split2[0];
                    var[i] = Split2[1];
                    break;
                default:
                    label[i] = ";";
                    inst[i] = Split2[0];
                    var[i] = ";";
                    break;
            }
        /**---------------ADDRESS-------------**/
            if(inst[i].equals("START"))
            {
                    address[i] = var[i];
                if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                    address[i+1] = var[i];
                if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                    result = Integer.parseInt(var[i],16);
                    prevRes=result;
            }
            else if(inst[i].equals("RESW"))
            {
                    result=(3*Integer.valueOf(var[i]))+prevRes;
                    address[i+1] =Integer.toHexString(result);
                if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                    prevRes=result;
                }
                else if(inst[i].equals("RESB"))
                {
                result=Integer.valueOf(var[i])+prevRes;
                address[i+1] = Integer.toHexString(result);
                if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                prevRes=result;
            }
                else if(inst[i].equals("BYTE"))
                {
                if(var[i].charAt(0)=='X'){
                    result=((var[i].length()-3)/2)+prevRes;
                    address[i+1] = Integer.toHexString(result);
                    if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                    prevRes=result;
                }
                else if(var[i].charAt(0)=='C')
                {
                    result=(var[i].length()-3)+prevRes;
                    address[i+1] =Integer.toHexString(result);
                    if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                    prevRes=result;
                }
            }
            else if(inst[i].charAt(0)=='+')
            {
                result=4+prevRes;
                address[i+1] =Integer.toHexString(result);
                if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                prevRes=result;  
            }
            else if(inst[i].equals("BASE"))
            {
                result=prevRes;
                address[i+1] =Integer.toHexString(result);
                if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                prevRes=result;
            }
            else{
                int flag = 0;
                for(int k=0;k<Converter.OPTAB.length;k++)
                {
                    if(inst[i].equals(Converter.OPTAB[k][0]) && flag==0){
                    formatType = Converter.OPTAB[k][1];
                    flag = 1; 
                    }  
                }
                flag =0;
            switch (formatType) {
                case "1":
                    result=Integer.parseInt(formatType)+prevRes;
                    address[i+1] =Integer.toHexString(result);
                    if (address[i+1].length() == 1)
                    {
                        address[i+1] = "000" + address[i+1];
                    }
                    else if (address[i+1].length()==2)
                    {
                        address[i+1] = "00" + address[i+1];
                    }
                    prevRes=result;
                    break;
                case "2":
                    result=Integer.parseInt(formatType)+prevRes;
                    address[i+1] =Integer.toHexString(result);
                    if (address[i+1].length() == 1)
                    {
                        address[i+1] = "000" + address[i+1];
                    }
                    else if (address[i+1].length()==2)
                    {
                        address[i+1] = "00" + address[i+1];
                    }
                    prevRes=result;
                    break;
                default:
                    String[] y = new String[10];
                    y = var[i].split(",");
                    if(y.length>1&& inst[i].equals("WORD")){
                        result=(3*y.length)+prevRes;
                        address[i+1] =Integer.toHexString(result);
                        if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                        prevRes=result;
                    }
                    else{
                        result=Integer.parseInt(formatType)+prevRes;
                        address[i+1] =Integer.toHexString(result);
                        if (address[i+1].length() == 1)
                        {
                            address[i+1] = "000" + address[i+1];
                        }
                        else if (address[i+1].length()==2)
                        {
                            address[i+1] = "00" + address[i+1];
                        }
                        prevRes=result;
                    }
                    break;
            }
            }
    i++;
}
/**----------------------------------Symbol Table----------------------------------**/
    HashMap<String, String> map = new HashMap<>();
            System.err.println("----Symbol Table----");
        for(int k = 0; k<46;k++)
        {
            if(label[k].equals(";"))
            {
                continue;
            }
            else
            {
                if (k == 0)
                {
                   // System.out.println(label[k] + " " + "000" +address[k]);
                    map.put(label[k],"000" + address[k]);   
                }
                else
                {
                   // System.out.println(label[k] + " " + address[k]);
                    map.put(label[k], address[k]);
                }
            }
        }
        System.out.println();
/**----------------------------------OPCODE----------------------------------**/

int flag = 0,decimal = 0;
String base = null , p = null,opBin = null,disp=null,Format=null,t=null,io=null,nix=null;
String []nixbpe = new String[6];
String opcode [] = new String[100];
String modif[] = new String[100];
opcode[0] = "No OPCODE for START";
for(i =0; i < 46; i++)
{   
    p= null;
    opBin=null;
    for(j=0;j<Converter.OPTAB.length;j++)
    {
        if (inst[i].contains("+"))
        {
        if(inst[i].substring(1).equals(Converter.OPTAB[j][0]))
        {
            p = Converter.OPTAB[j][2];
            Format = Converter.OPTAB[j][1];
        }
        }
        else
        {
            if(inst[i].equals(Converter.OPTAB[j][0]))
        {
            p = Converter.OPTAB[j][2];
            Format= Converter.OPTAB[j][1];
        }
        }
    }
    if (inst[i].contains("+"))
    {
        if(var[i].contains("#"))
        {
            nixbpe[0] = "0";
            nixbpe[1] = "1";
            nixbpe[2] = "0";
            nixbpe[3] = "0";
            nixbpe[4] = "0";
            nixbpe[5] = "1";
            opBin = hexToBin(p);
            opBin = opBin.substring(0, 6);
            opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
            opBin = binaryToHex(opBin);
            nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
            nix = binaryToHex(nix);
            if (isNumeric(var[i].substring(1)))
                    {
                        int conv = Integer.parseInt(var[i].substring(1));
                        String temp = Integer.toHexString(conv);
                        opcode[i] = opBin+""+nix+"0"+temp;
                        modif[i] = "M^ " + decToHex(hexToDec(address[i])+1) + " ^ " + "05";
                    }
            else
            {
                opcode[i] = opBin+""+nix+"0"+map.get(var[i].substring(1));
                modif[i] = "M^ " + decToHex(hexToDec(address[i])+1) + " ^ " + "05";
            }
        }
        else
        {
            nixbpe[0] = "1";
            nixbpe[1] = "1";
            nixbpe[2] = "0";
            nixbpe[3] = "0";
            nixbpe[4] = "0";
            nixbpe[5] = "1";
            opBin = hexToBinary(p);
            opBin = opBin.substring(0, 5);
            opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
            opBin = binaryToHex(opBin);
            nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
            nix = binaryToHex(nix);
            opcode[i] = opBin+""+nix+"0"+map.get(var[i]);
            modif[i] = "M^ " + decToHex(hexToDec(address[i])+1) + " ^ " + "05";
        }
    }
    else if("3".equals(Format))
    {
        if (inst[i].equals("RESW") || inst[i].equals("BYTE") || inst[i].equals("WORD") || inst[i].equals("RESB"))
            
        {
            opcode[i] = var[i];
            continue;
        }
        else if(inst[i].equals("END"))
        {
            opcode[i] = "NO OPCODE for END";
            continue;
        }
        else if (inst[i].equals("START"))
        {
            continue;
        }
        else if(inst[i].equals("BASE"))
        {
                opcode[i] = "No OPCODE for BASE";
                base = map.get(var[i]);
                continue;
        }
        else
        {
            if(p!=null)
            {
            opBin = hexToBin(p);
            opBin = opBin.substring(0, 6);
            if(var[i].contains("#"))
            {
                if(var[i].equals("#0") || var[i].equals("#3"))
                {
                    nixbpe[0] = "0";
                    nixbpe[1] = "1";
                    nixbpe[2] = "0";
                    nixbpe[3] = "0";
                    nixbpe[4] = "0";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+var[i].substring(1);
                }
                else
                {
                t = map.get(var[i].substring(1));
                io = decimalToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(address[i+1]));
                int cond = hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(address[i+1]);
                if(-2084 <= cond || cond <= 2047)
                {
                    nixbpe[0] = "0";
                    nixbpe[1] = "1";
                    nixbpe[2] = "0";
                    nixbpe[3] = "0";
                    nixbpe[4] = "1";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                }
                else if (0 <= cond || cond <= 4095 )
                {
                    io = decimalToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(base));
                    nixbpe[0] = "0";
                    nixbpe[1] = "1";
                    nixbpe[2] = "0";
                    nixbpe[3] = "1";
                    nixbpe[4] = "0";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                }
                }
            }
            else if (var[i].contains("@"))
            {
                    t = map.get(var[i].substring(1));
                    io = decToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(binaryToHex(hexToBinary(address[i+1]))));
                    int cond = hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(binaryToHex(hexToBinary(address[i+1])));
                    if(-2084 <= cond || cond <= 2047)
                    {

                    nixbpe[0] = "1";
                    nixbpe[1] = "0";
                    nixbpe[2] = "0";
                    nixbpe[3] = "0";
                    nixbpe[4] = "1";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                    }
                    else if(0 <= cond || cond <= 4095)
                    {
                        io = decToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(binaryToHex(hexToBinary(base))));
                    nixbpe[0] = "1";
                    nixbpe[1] = "0";
                    nixbpe[2] = "0";
                    nixbpe[3] = "1";
                    nixbpe[4] = "0";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                    }
            }
            else if (var[i].contains(",X"))
            {
                    t = map.get(var[i].substring(0, 6));
                    io = decToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(address[i+1]));
                    int cond = hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(address[i+1]);
                    if(0 <= cond || cond <= 4095)
                    {
                    io = decToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(base));
                    nixbpe[0] = "1";
                    nixbpe[1] = "1";
                    nixbpe[2] = "1";
                    nixbpe[3] = "1";
                    nixbpe[4] = "0";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                    }
                    else if(-2084 <= cond || cond <= 2047)
                    {
                    nixbpe[0] = "1";
                    nixbpe[1] = "1";
                    nixbpe[2] = "1";
                    nixbpe[3] = "0";
                    nixbpe[4] = "1";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                    }
            }
            else if(!inst[i].equals("RSUB"))
            {
                    t = map.get(var[i]);
                    io = decToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(address[i+1]));
                    int cond = hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(address[i+1]);
                    if(-2084 <= cond && cond <= 2047)
                    {
                    nixbpe[0] = "1";
                    nixbpe[1] = "1";
                    nixbpe[2] = "0";
                    nixbpe[3] = "0";
                    nixbpe[4] = "1";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
                    }
                    else if(0 <= cond && cond <= 4095 || cond < -2084)
                    {
                        io = decToHex(hexToDec(binaryToHex(hexToBinary(t))) - hexToDec(base));
                    nixbpe[0] = "1";
                    nixbpe[1] = "1";
                    nixbpe[2] = "0";
                    nixbpe[3] = "1";
                    nixbpe[4] = "0";
                    nixbpe[5] = "0";
                    opBin = opBin+""+nixbpe[0]+""+nixbpe[1];
                    opBin = binaryToHex(opBin);
                    nix = nixbpe[2]+""+nixbpe[3]+""+nixbpe[4]+""+nixbpe[5];
                    decimal = Integer.parseInt(nix,2);
                    nix = Integer.toString(decimal,16);
                    opcode[i] = opBin+""+nix+""+io;
               //     System.out.println(opBin+""+nix+""+io);
                    }
            }
            else if(inst[i].equals("RSUB"))
            {
                opcode[i]=decToHex(hexToDec(binaryToHex(hexToBinary(p))) + 3) + "0000";
            }
            }
    }
    }
    else if ("2".equals(Format))
    {
         HashMap<String, String> R = new HashMap<>();
        R.put("X", "1");
        R.put("A", "0");
        R.put("S", "4");
        R.put("T", "5");
        String Sp=var[i].replace(",", " ");
        if (var[i].length() > 1)
        {
            opcode[i] = p + "" + R.get(Sp.substring(0,1)) + ""+R.get(Sp.substring(2));
        }
        else
        {
            opcode[i] = p + "" + R.get(Sp) + "0";
        }
    }
}
System.out.println(map);
System.err.println("LABEL\t\t\tINSTRUCTIONS\t\tVARIABLES\t\tADDRESS\t\t\tOPCODE");
System.out.println("");
String EndAddress = null;
for (i = 0; i < 46; i++)
{
     System.out.println(label[i] + "\t\t\t" + inst[i] + "\t\t\t" + var[i] + "\t\t\t" + address[i]+"\t\t\t"+opcode[i]);
     EndAddress = address[i];
}
            System.out.println();
            System.out.println("\t\t\t\t\t\t\t\tHTE Record\t\t\t");
System.out.println("H^"+progName+"^"+"000"+strtAddress+"^"+decToHex(hexToDec(EndAddress) - hexToDec(strtAddress)));
int l = 1;
            System.out.print("T^ " + address[0] + " ^ ");
            scan2.reset();
            int count = 0;
while(l!=46)
{
    if(inst[l].equalsIgnoreCase("resb") || inst[l].equalsIgnoreCase("resw") || inst[l].equalsIgnoreCase("word") || inst[l].equalsIgnoreCase("byte") || opcode[l].equalsIgnoreCase("no opcode for base") || opcode[l].equalsIgnoreCase("no opcode for end"))
    {
        l++;
        count++;
        continue;
    }
    else
    {
    System.out.print(opcode[l]);
    l++;
    count++;
    }
    if(count==10)
    {
        System.out.println();
        System.out.print("T^ ");
        count = 0;
    }
}
            System.out.println();
            System.out.println("E^ " + address[0]);
            for (i = 0; i < 100; i++)
            {
                if(modif[i] == null)
                {
                    continue;
                }
                else
                {
                    System.out.println(modif[i]);
                }
            }
}          
}
