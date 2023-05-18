import java.util.ArrayList;

public class MainSwitch {
    public static void main(String[] args) {

        ArrayList<Puzzel3> Puzzels = MPSwitch.generate_puzzels();

        for (int i = 0; i < Puzzels.size(); i++) {
            System.out.println("Cifrado: "+ Puzzels.get(i).getCriptograma() + " Hash: " +Puzzels.get(i).getHash());
        }
        Bob b = MPSwitch.solv_puzzel(Puzzels);
        System.out.println("Segredo: " + b.getTexto_limpo() + " Escolha: " + b.getEscolha());
    }
}
