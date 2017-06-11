import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by jonnykong on 28/04/2017.
 */
public class app {
    private JPanel panel;
    private JButton inverseButton;
    private JButton compositionButton;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JButton restrictionButton;
    private JButton imageButton;
    private JButton singleRootedButton;
    private JButton singleValuedButton;
    private JButton symmetricButton;
    private JButton reflexiveButton;
    private JButton reflexive_ClosureButton;
    private JButton transitiveButton;
    private JButton transitive_ClosureButton;
    private JButton symmetric_ClosureButton;

    private HashMap<Integer, HashSet<Integer>> domain_input1;
    private HashMap<Integer, HashSet<Integer>> range_input1;
    private HashMap<Integer, HashSet<Integer>> domain_input2;
    private HashMap<Integer, HashSet<Integer>> range_input2;
    private HashMap<Integer, HashSet<Integer>> domain_result;
    private HashMap<Integer, HashSet<Integer>> range_result;
    private HashSet<Integer> domain_set;    // Input is set in textArea2


    public app() {
        inverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inverseButtonActionPerformed(e);
            }
        });
        compositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compositionButtonActionPerformed(e);
            }
        });
        restrictionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { restrictionButtonActionPerformed(e); }
        });
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { imageButtonActionPerformed(e); }
        });
        singleRootedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { singleRootedButtonActionPerformed(e); }
        });
        singleValuedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { singleValuedButtonActionPerformed(e); }
        });
        symmetricButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { symmetricButtonActionPerformed(e); }
        });
        reflexiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { reflexiveButtonActionPerformed(e); }
        });
        reflexive_ClosureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { reflexive_ClosureButtonActionPerformed(e); }
        });
        transitiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { transitiveButtonActionPerformed(e); }
        });
        transitive_ClosureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { transitive_ClosureButtonActionPerformed(e); }
        });
        symmetric_ClosureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { symmetric_ClosureButtonActionPerformed(e); }
        });

        textArea1.setLineWrap(true);
        textArea2.setLineWrap(true);
        textArea3.setLineWrap(true);

        domain_input1 = new HashMap<Integer, HashSet<Integer>>();
        range_input1 = new HashMap<Integer, HashSet<Integer>>();
        domain_input2 = new HashMap<Integer, HashSet<Integer>>();
        range_input2 = new HashMap<Integer, HashSet<Integer>>();
        domain_result = new HashMap<Integer, HashSet<Integer>>();
        range_result = new HashMap<Integer, HashSet<Integer>>();
        domain_set = new HashSet<Integer>();
    }

    private void inverseButtonActionPerformed(ActionEvent e) {
        // Parse input strings
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception emptyString) {
            str1 = "";
        }
        try {
            parseBinaryRelationInput(str2, this.domain_input2, this.range_input2);
        } catch (Exception emptyString) {
            str2 = "";
        }
        String str3 = printBinaryRelation(this.range_input1);
        String str4 = printBinaryRelation(this.range_input2);
        textArea3.setText("");
        if(str3.length() > 0) {
            textArea3.append(str3);
        }
        if(str4.length() > 0) {
            textArea3.append("\n");
            textArea3.append(str4);
        }
    }

    private void compositionButtonActionPerformed(ActionEvent e) {
        // Parse input strings
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception emptyString) {}
        try {
            parseBinaryRelationInput(str2, this.domain_input2, this.range_input2);
        } catch (Exception emptyString) {}
        // Create set of common elements between range1 and domain2
        HashSet common_elements = new HashSet<Integer>();
        for(Integer element_range1 : this.range_input1.keySet()) {
            if(this.domain_input2.containsKey(element_range1)) {
                common_elements.add(element_range1);
            }
        }
        // Store result in Hashmap result, clear it first
        this.domain_result.clear();
        this.range_result.clear();
        // Iterate through all possible combinations
        for(Integer a : this.domain_input1.keySet()) {
            for(Integer b : this.domain_input1.get(a)) {
                // Not exist in common elements between the two graphs
                if(common_elements.contains(b) == false) {
                    continue;
                }
                else {
                    for(Integer c : this.domain_input2.get(b)) {
                        if(this.domain_result.containsKey(a) == false) this.domain_result.put(a, new HashSet<Integer>());
                        if(this.domain_result.get(a).contains(c) == false) this.domain_result.get(a).add(c);
                        if(this.range_result.containsKey(c) == false) this.range_result.put(c, new HashSet<Integer>());
                        if(this.range_result.get(c).contains(a) == false) this.range_result.get(c).add(a);
                    }
                }
            }
        }
        String result_str = printBinaryRelation(this.domain_result);
        if(result_str.length() > 0) {
            textArea3.setText(result_str);
        }
    }

    private void restrictionButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.domain_input2);
        } catch (Exception EmptyString) {}
        try {
            parseSetInput(str2, this.domain_set);
        } catch (Exception EmptyString) {}
        // Add all relations to result with common domain with set
        this.domain_result.clear();
        this.range_result.clear();
        for(Integer first : this.domain_input1.keySet()) {
            if(this.domain_set.contains(first)) {
                this.domain_result.put(first, new HashSet<Integer>());
                for(Integer second : this.domain_input1.get(first)) {
                    this.domain_result.get(first).add(second);
                }
            }
        }
        String result_str = printBinaryRelation(this.domain_result);
        if(result_str.length() > 0) {
            textArea3.setText(result_str);
        }
    }

    private void imageButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception EmptyString) {}
        try {
            parseSetInput(str2, this.domain_set);
        } catch (Exception EmptyString) {}
//        this.domain_result.clear();
//        this.range_result.clear();
        HashSet<Integer> domain_set_new = new HashSet<Integer>();
        for(Integer first : this.domain_input1.keySet()) {
            if(this.domain_set.contains(first)) {
                for(Integer second : this.domain_input1.get(first)) {
                    domain_set_new.add(second);
                }
            }
        }
        String result_str = printSet(domain_set_new);
        if(result_str.length() > 0) {
            textArea3.setText(result_str);
        }
    }

    private void singleRootedButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception emptyString) {}
        try {
            parseBinaryRelationInput(str2, this.domain_input2, this.range_input2);
        } catch (Exception emptyString) {}
        boolean isSingleRooted1 = isSingleRooted(this.domain_input1, this.range_input1);
        boolean isSingleRooted2 = isSingleRooted(this.domain_input2, this.range_input2);
        textArea3.setText("");
        if(str1.length() > 0) {
            if(isSingleRooted1) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
        if(str2.length() > 0) {
            if(str1.length() > 0) {
                textArea3.append("\n");
            }
            if(isSingleRooted2) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
    }

    private void singleValuedButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception emptyString) {}
        try {
            parseBinaryRelationInput(str2, this.domain_input2, this.range_input2);
        } catch (Exception emptyString) {}
        boolean isSingleValued1 = isSingleValued(this.domain_input1, this.range_input1);
        boolean isSingleValued2 = isSingleValued(this.domain_input2, this.range_input2);
        textArea3.setText("");
        if(str1.length() > 0) {
            if(isSingleValued1) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
        if(str2.length() > 0) {
            if(str1.length() > 0) {
                textArea3.append("\n");
            }
            if(isSingleValued2) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
    }

    private void symmetricButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception emptyString) {}
        try {
            parseBinaryRelationInput(str2, this.domain_input2, this.range_input2);
        } catch (Exception emptyString) {}
        boolean isSymmetrical1 = isSymmetrical(this.domain_input1, this.range_input1);
        boolean isSymmetrical2 = isSymmetrical(this.domain_input2, this.range_input2);
        textArea3.setText("");
        if(str1.length() > 0) {
            if(isSymmetrical1) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
        if(str2.length() > 0) {
            if(str1.length() > 0) {
                textArea3.append("\n");
            }
            if(isSymmetrical2) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
    }

    private void reflexiveButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception emptyString) {}
        try {
            parseBinaryRelationInput(str2, this.domain_input2, this.range_input2);
        } catch (Exception emptyString) {}
        boolean isReflexive1 = isReflexive(this.domain_input1, this.range_input1);
        boolean isReflexive2 = isReflexive(this.domain_input2, this.range_input2);
        textArea3.setText("");
        if(str1.length() > 0) {
            if(isReflexive1) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
        if(str2.length() > 0) {
            if(str1.length() > 0) {
                textArea3.append("\n");
            }
            if(isReflexive2) {
                textArea3.append("True");
            }
            else {
                textArea3.append("False");
            }
        }
    }

    public void reflexive_ClosureButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception EmptyString) {}
        try {
            parseSetInput(str2, this.domain_set);
        } catch (Exception EmptyString) {}
        createReflexiveClosure(this.domain_input1, this.domain_input2, this.domain_set);
        String result_str = printBinaryRelation(this.domain_input1);
        if(result_str.length() > 0) {
            textArea3.setText(result_str);
        }
    }

    public void transitiveButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception EmptyString) {}
        try {
            parseSetInput(str2, this.domain_set);
        } catch (Exception EmptyString) {}
        boolean isTransitive1 = isTransitive(this.domain_input1, this.domain_set);
        textArea3.setText("");
        if(isTransitive1 == true) {
            textArea3.setText("True");
        }
        else {
            textArea3.setText("False");
        }
    }

    public void transitive_ClosureButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception EmptyString) {}
        try {
            parseSetInput(str2, this.domain_set);
        } catch (Exception EmptyString) {}
        createTransitiveClosure(this.domain_input1, this.domain_set);
        textArea3.setText("");
        String result_str = printBinaryRelation(this.domain_input1);
        if(result_str.length() > 0) {
            textArea3.setText(result_str);
        }
    }

    public void symmetric_ClosureButtonActionPerformed(ActionEvent e) {
        String str1 = textArea1.getText();
        String str2 = textArea2.getText();
        try {
            parseBinaryRelationInput(str1, this.domain_input1, this.range_input1);
        } catch (Exception EmptyString) {}
        try {
            parseSetInput(str2, this.domain_set);
        } catch (Exception EmptyString) {}
        createSymmetricClosure(this.domain_input1);
        createSymmetricClosure(this.domain_input2);
        textArea3.setText("");
        String result_str1 = printBinaryRelation(this.domain_input1);
        if(str1.length() > 0) {
            textArea3.setText(result_str1);
        }
    }


    //////////// Non-GUI functions /////////////
    private void parseBinaryRelationInput(String str, HashMap<Integer, HashSet<Integer>> domain, HashMap<Integer, HashSet<Integer>> range) {
        domain.clear();
        range.clear();
        // Parse String
        String subStr = str.substring(2, str.length() - 2);
        String[] split = subStr.split(">,<");
        for(int i = 0; i < split.length; ++i) {
            String pair[] = split[i].split(",");
            Integer first = Integer.parseInt(pair[0]);
            Integer second = Integer.parseInt(pair[1]);
            // Add pair to domain and range maps
            if (domain.containsKey(first) == false) {
                domain.put(first, new HashSet<Integer>());
            }
            domain.get(first).add(second);
            if (range.containsKey(second) == false) {
                range.put(second, new HashSet<Integer>());
            }
            range.get(second).add(first);
        }
    }

    private void parseSetInput(String str, HashSet<Integer> domain) {
        domain.clear();
        String subStr = str.substring(1, str.length() - 1);
        String[] split = subStr.split(",");
        for(int i = 0; i < split.length; ++i) {
            Integer num = Integer.parseInt(split[i]);
            if(domain.contains(num) == false) {
                domain.add(num);
            }
        }
    }

    private String printBinaryRelation(HashMap<Integer, HashSet<Integer>> domain) {
        StringBuilder s = new StringBuilder("{");
        for(Integer first : domain.keySet()) {
            Iterator<Integer> it = domain.get(first).iterator();
            while(it.hasNext()) {
                s.append('<');
                s.append(first);
                s.append(',');
                s.append(it.next());
                s.append(">,");
            }
        }
        s.deleteCharAt(s.length() - 1);
        if(s.length() > 0) {
            s.append('}');
            return s.toString();
        }
        else {
            return new String("Empty");
        }
    }

    private String printSet(HashSet<Integer> set) {
        StringBuilder s = new StringBuilder("{");
        for(Integer i : set) {
            s.append(i);
            s.append(",");
        }
        s.deleteCharAt(s.length() - 1);
        if(s.length() > 0) {
            s.append('}');
            return s.toString();
        }
        else {
            return new String("Empty");
        }
    }

    private boolean isSingleRooted(HashMap<Integer, HashSet<Integer>> domain, HashMap<Integer, HashSet<Integer>> range) {
        return isSingleValued(range, domain);
    }

    private boolean isSingleValued(HashMap<Integer, HashSet<Integer>> domain, HashMap<Integer, HashSet<Integer>> range) {
        for(Integer first : domain.keySet()) {
            if(domain.get(first).size() > 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isSymmetrical(HashMap<Integer, HashSet<Integer>> domain, HashMap<Integer, HashSet<Integer>> range) {
        if(domain.keySet().equals(range.keySet()) == false) return false;
        for(Integer first : domain.keySet()) {
            if(domain.get(first).equals(range.get(first)) == false) {
                return false;
            }
        }
        return true;
    }

    private boolean isReflexive(HashMap<Integer, HashSet<Integer>> domain, HashMap<Integer, HashSet<Integer>> range) {
        for(Integer first : domain.keySet()) {
            if(domain.get(first).contains(first) == false) {
                return false;
            }
        }
        return true;
    }

    private void createReflexiveClosure(HashMap<Integer, HashSet<Integer>> domain, HashMap<Integer, HashSet<Integer>> range, HashSet<Integer> set) {
        for(Integer i : set) {
            if(domain.keySet().contains(i) == false) {
                domain.put(i, new HashSet<Integer>());
            }
            if(domain.get(i).contains(i) == false) {
                domain.get(i).add(i);
            }
        }
    }

    private boolean isTransitive(HashMap<Integer, HashSet<Integer>> domain, HashSet<Integer> set) {
        for(Integer a : set) {
            for(Integer b : set) {
                for(Integer c : set) {
                    if(domain.get(a).contains(b) && domain.get(b).contains(c)) {
                        if(domain.get(a).contains(c) == false) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void createTransitiveClosure(HashMap<Integer, HashSet<Integer>> domain, HashSet<Integer> set) {
        for(Integer a : set) {
            for(Integer b : set) {
                for(Integer c : set) {
                    if(domain.get(a).contains(b) && domain.get(b).contains(c)) {
                        if(domain.get(a).contains(c) == false) {
                            domain.get(a).add(c);
                        }
                    }
                }
            }
        }
    }

    private void createSymmetricClosure(HashMap<Integer, HashSet<Integer>> domain) {
        HashSet<Integer> keySet = new HashSet<Integer>();
        keySet.addAll(domain.keySet());
        for (Integer a : keySet) {
            for (Integer b : domain.get(a)) {
                if(domain.keySet().contains(b) == false) {
                    domain.put(b, new HashSet<Integer>());
                }
                if (domain.get(b).contains(a) == false) {
                    domain.get(b).add(a);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("app");
        frame.setContentPane(new app().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}