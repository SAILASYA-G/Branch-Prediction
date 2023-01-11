import java.io.*;
import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;
import java.util.HashMap;
class sim
{
    static void call_smith(int base_value, File file) throws FileNotFoundException
    {
        int total_lines_count=0, misprediction_count=0;
        char branch_predictor = 't';
        int mid_value_initial = (int)(Math.pow(2, base_value)/2);
        int mid_value = (int)(Math.pow(2, base_value)/2);
        int max_value = (int)(Math.pow(2,base_value)-1);
		Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
        {
            total_lines_count++;
            String misprediction_lines = sc.nextLine();
            String[] file_string = misprediction_lines.split(" ");
            char sub_line = file_string[1].charAt(0);
            if (sub_line!=branch_predictor)
            {
                misprediction_count++;
            }
            if (sub_line=='n')
            {
                if (mid_value>0)
                    mid_value--;
                if (0 <= mid_value && mid_value<= mid_value_initial-1)
                    branch_predictor='n';
                else
                    branch_predictor='t';
            }
            if (sub_line=='t')
            {
                if (mid_value<max_value)
                    mid_value++;
                if (mid_value_initial <= mid_value && mid_value <= max_value)
                    branch_predictor='t';
                else
                    branch_predictor='n';
            }
	    }     
        sc.close();
        System.out.println("number of predictions:\t\t"+total_lines_count);
        System.out.println("number of mispredictions:\t"+misprediction_count);
        double misprediction_rate = ((double) misprediction_count/(total_lines_count)) * 100;
        System.out.println("misprediction rate:\t\t" + String.format("%.2f",misprediction_rate) +"%");
        System.out.println("FINAL COUNTER CONTENT:\t\t" + mid_value);
    }
    static String hex_to_binary(String input_hex)
    {
        StringBuffer hex_string = new StringBuffer();
        HashMap<Character, String> intermediate_hex_array = new HashMap<Character, String>();
        intermediate_hex_array.put('0',"0000");
		intermediate_hex_array.put('1', "0001");
		intermediate_hex_array.put('2', "0010");
		intermediate_hex_array.put('3', "0011");
		intermediate_hex_array.put('4', "0100");
		intermediate_hex_array.put('5', "0101");
		intermediate_hex_array.put('6', "0110");
		intermediate_hex_array.put('7', "0111");
		intermediate_hex_array.put('8', "1000");
		intermediate_hex_array.put('9', "1001");
		intermediate_hex_array.put('A', "1010");
		intermediate_hex_array.put('B', "1011");
		intermediate_hex_array.put('C', "1100");
		intermediate_hex_array.put('D', "1101");
		intermediate_hex_array.put('E', "1110");
		intermediate_hex_array.put('F', "1111");
        for (int i=0; i<input_hex.length();i++)
        {
            hex_string.append(intermediate_hex_array.get(input_hex.toUpperCase().charAt(i)));
        }
        return hex_string.toString();
    }
    public static void main (String[] args) throws IOException
    {
        if (args[0].equals("smith"))
        {
            int base_value = Integer.parseInt(args[1]);
            File file = new File(args[2]);
            call_smith(base_value, file);
        }
        if (args[0].equals("bimodal"))
        {
            int base_value = Integer.parseInt(args[1]);
            int[] bimodal_array = new int[(int)Math.pow(2,base_value)];
            Arrays.fill(bimodal_array,4);
            File file = new File(args[2]);
            char branch_predictor;
            int total_lines_count=0, misprediction_count=0;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                String hex_values = sc.nextLine();
                String[] file_string_1 = hex_values.split(" ");
                StringBuffer binary_address = new StringBuffer(hex_to_binary(file_string_1[0]));
                StringBuffer temp_binary_address = new StringBuffer(binary_address.substring(0, binary_address.length() - 2));
                StringBuffer new_binary_address = new StringBuffer(temp_binary_address.substring(temp_binary_address.length()-base_value));
                int index_value = Integer.parseInt(new_binary_address.toString(),2);
                total_lines_count++;
                char sub_line = file_string_1[1].charAt(0);
                if (bimodal_array[index_value]<4)
                    branch_predictor='n';
                else
                    branch_predictor='t';
                if (sub_line!=branch_predictor)
                {
                    misprediction_count++;
                }
                if (sub_line=='n')
                {
                    if (bimodal_array[index_value]>0)
                        bimodal_array[index_value]--;
                    if (0 <= bimodal_array[index_value] && bimodal_array[index_value]<= 3)
                        branch_predictor='n';
                    else
                        branch_predictor='t';
                }
                if (sub_line=='t')
                {
                    if (bimodal_array[index_value]<7)
                        bimodal_array[index_value]++;
                    if (4 <= bimodal_array[index_value] && bimodal_array[index_value] <= 7)
                        branch_predictor='t';
                    else
                        branch_predictor='n';
                }
	        }     
            sc.close();
            System.out.println("number of predictions:\t\t"+total_lines_count);
            System.out.println("number of mispredictions:\t"+misprediction_count);
            double misprediction_rate = ((double) misprediction_count/(total_lines_count)) * 100;
            System.out.println("misprediction rate:\t\t" + String.format("%.2f",misprediction_rate) +"%");
            System.out.println("FINAL BIMODAL CONTENTS");
            for (int i=0;i<bimodal_array.length;i++)
            {
                System.out.println(i+"	"+bimodal_array[i]);
            }
        }
        if (args[0].equals("gshare"))
        {
            int base_value_m = Integer.parseInt(args[1]);
            int base_value_n = Integer.parseInt(args[2]);
            int[] gshare_array = new int[(int)Math.pow(2,base_value_m)];
            Arrays.fill(gshare_array,4);
            char[] history_register_old = new char[base_value_n];
            Arrays.fill(history_register_old,'0');
            StringBuffer history_register_new = new StringBuffer();
            history_register_new.append(history_register_old);
            File file = new File(args[3]);
            char branch_predictor;
            int total_lines_count=0, misprediction_count=0;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                String hex_values = sc.nextLine();
                String[] file_string_1 = hex_values.split(" ");
                StringBuffer binary_address = new StringBuffer(hex_to_binary(file_string_1[0]));
                StringBuffer temp_binary_address = new StringBuffer(binary_address.substring(0, binary_address.length() - 2));
                StringBuffer new_binary_address = new StringBuffer(temp_binary_address.substring(temp_binary_address.length()-base_value_m));
                StringBuffer first_part = new StringBuffer(new_binary_address.substring(0,new_binary_address.length()-base_value_n));
                StringBuffer second_part = new StringBuffer(new_binary_address.substring(new_binary_address.length()-base_value_n));
                StringBuffer temporary_string=new StringBuffer();
                for(int i = 0; i < base_value_n; i++)
                {
                    if (second_part.charAt(i)==history_register_new.charAt(i))
                        temporary_string.append("0");
                    else
                        temporary_string.append("1");
                }
                first_part.append(temporary_string);
                int index_value = Integer.parseInt(first_part.toString(),2);
                total_lines_count++;
                char sub_line = file_string_1[1].charAt(0);
                if (gshare_array[index_value]<4)
                    branch_predictor='n';
                else
                    branch_predictor='t';
                if (sub_line!=branch_predictor)
                    misprediction_count++;
                if (sub_line=='n')
                {
                    history_register_new.delete(history_register_new.length()-1,history_register_new.length());
                    history_register_new.insert(0,"0");
                    if (gshare_array[index_value]>0)
                        gshare_array[index_value]--;
                    if (0 <= gshare_array[index_value] && gshare_array[index_value]<= 3)
                        branch_predictor='n';
                    else
                        branch_predictor='t';
                }
                if (sub_line=='t')
                {
                    history_register_new.delete(history_register_new.length()-1,history_register_new.length());
                    history_register_new.insert(0,"1");
                    if (gshare_array[index_value]<7)
                        gshare_array[index_value]++;
                    if (4 <= gshare_array[index_value] && gshare_array[index_value] <= 7)
                        branch_predictor='t';
                    else
                        branch_predictor='n';
                }
	        }     
            sc.close();
            System.out.println("number of predictions:\t\t"+total_lines_count);
            System.out.println("number of mispredictions:\t"+misprediction_count);
            double misprediction_rate = ((double) misprediction_count/(total_lines_count)) * 100;
            System.out.println("misprediction rate:\t\t" + String.format("%.2f",misprediction_rate) +"%");
            System.out.println("FINAL GSHARE CONTENTS");
            for (int i=0;i<gshare_array.length;i++)
            {
                System.out.println(i+"	"+gshare_array[i]);
            }
        }
        if (args[0].equals("hybrid"))
        {
            int base_value_k = Integer.parseInt(args[1]);
            int base_value_m1 = Integer.parseInt(args[2]);
            int base_value_n1 = Integer.parseInt(args[3]);
            int base_value_m2 = Integer.parseInt(args[4]);
            int[] hybrid_array = new int[(int)Math.pow(2,base_value_k)];
            Arrays.fill(hybrid_array,1);
            int[] gshare_array = new int[(int)Math.pow(2,base_value_m1)];
            Arrays.fill(gshare_array,4);
            char[] history_register_old = new char[base_value_n1];
            Arrays.fill(history_register_old,'0');
            StringBuffer history_register_new = new StringBuffer();
            history_register_new.append(history_register_old);
            int[] bimodal_array = new int[(int)Math.pow(2,base_value_m2)];
            Arrays.fill(bimodal_array,4);
            File file = new File(args[5]);
            char branch_predictor_bimodal, branch_predictor_gshare;
            int total_lines_count=0, misprediction_count=0;
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
            {
                String hex_values = sc.nextLine();
                String[] file_string_1 = hex_values.split(" ");
                StringBuffer binary_address = new StringBuffer(hex_to_binary(file_string_1[0]));
                StringBuffer temp_binary_address = new StringBuffer(binary_address.substring(0, binary_address.length() - 2));
                StringBuffer new_binary_address_hybrid = new StringBuffer(temp_binary_address.substring(temp_binary_address.length()-base_value_k));
                StringBuffer new_binary_address_bimodal = new StringBuffer(temp_binary_address.substring(temp_binary_address.length()-base_value_m2));
                StringBuffer new_binary_address_gshare = new StringBuffer(temp_binary_address.substring(temp_binary_address.length()-base_value_m1));
                StringBuffer first_part_gshare = new StringBuffer(new_binary_address_gshare.substring(0,new_binary_address_gshare.length()-base_value_n1));
                StringBuffer second_part_gshare = new StringBuffer(new_binary_address_gshare.substring(new_binary_address_gshare.length()-base_value_n1));
                int index_value_hybrid = Integer.parseInt(new_binary_address_hybrid.toString(),2);
                int index_value_bimodal = Integer.parseInt(new_binary_address_bimodal.toString(),2);
                StringBuffer temporary_string=new StringBuffer();
                for(int i = 0; i < base_value_n1; i++)
                {
                    if (second_part_gshare.charAt(i)==history_register_new.charAt(i))
                        temporary_string.append("0");
                    else
                        temporary_string.append("1");
                }
                first_part_gshare.append(temporary_string);
                int index_value_gshare = Integer.parseInt(first_part_gshare.toString(),2);
                total_lines_count++;
                char sub_line = file_string_1[1].charAt(0);

                if (bimodal_array[index_value_bimodal]<4)
                    branch_predictor_bimodal='n';
                else
                    branch_predictor_bimodal='t';

                if (gshare_array[index_value_gshare]<4)
                    branch_predictor_gshare='n';
                else
                    branch_predictor_gshare='t';

                if (hybrid_array[index_value_hybrid]>=2)
                {
                    if (branch_predictor_gshare!=sub_line)
                        misprediction_count++;
                    if (sub_line=='n')
                    {
                        history_register_new.delete(history_register_new.length()-1,history_register_new.length());
                        history_register_new.insert(0,"0");
                        if (gshare_array[index_value_gshare]>0)
                            gshare_array[index_value_gshare]--;
                    }
                    if (sub_line=='t')
                    {
                        history_register_new.delete(history_register_new.length()-1,history_register_new.length());
                        history_register_new.insert(0,"1");
                        if (gshare_array[index_value_gshare]<7)
                            gshare_array[index_value_gshare]++;
                    }
                }
                else
                {
                    if (branch_predictor_bimodal!=sub_line)
                        misprediction_count++;
                    if (sub_line=='n')
                    {
                        history_register_new.delete(history_register_new.length()-1,history_register_new.length());
                        history_register_new.insert(0,"0");
                        if (bimodal_array[index_value_bimodal]>0)
                            bimodal_array[index_value_bimodal]--;
                        
                    }
                    if (sub_line=='t')
                    {
                        history_register_new.delete(history_register_new.length()-1,history_register_new.length());
                        history_register_new.insert(0,"1");
                        if (bimodal_array[index_value_bimodal]<7)
                            bimodal_array[index_value_bimodal]++;
                    }
                }
                if (sub_line==branch_predictor_gshare && sub_line!=branch_predictor_bimodal)
                {
                    if (hybrid_array[index_value_hybrid]<3)
                        hybrid_array[index_value_hybrid]++;
                }
                if (sub_line!=branch_predictor_gshare && sub_line==branch_predictor_bimodal)
                {
                    if (hybrid_array[index_value_hybrid]>0)
                        hybrid_array[index_value_hybrid]--;
                }
            }
        sc.close();
        System.out.println("number of predictions:\t\t"+total_lines_count);
        System.out.println("number of mispredictions:\t"+misprediction_count);
        double misprediction_rate = ((double) misprediction_count/(total_lines_count)) * 100;
        System.out.println("misprediction rate:\t\t" + String.format("%.2f",misprediction_rate) +"%");
        System.out.println("FINAL CHOOSER CONTENTS");
        for (int i=0;i<hybrid_array.length;i++)
        {
            System.out.println(i+"	"+hybrid_array[i]);
        }
        System.out.println("FINAL GSHARE CONTENTS");
        for (int i=0;i<gshare_array.length;i++)
        {
            System.out.println(i+"	"+gshare_array[i]);
        }
        System.out.println("FINAL BIMODAL CONTENTS");
        for (int i=0;i<bimodal_array.length;i++)
        {
            System.out.println(i+"	"+bimodal_array[i]);
        }
    }
}}