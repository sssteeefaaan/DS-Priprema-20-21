import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

public class TagClient extends UnicastRemoteObject implements TagMessageCallback {

    private TagManager proxy;

    public TagClient(String host, String port, String service)
            throws RemoteException, MalformedURLException, NotBoundException {
        proxy = (TagManager) Naming.lookup("rmi://" + host + ":" + port + "/" + service);

        System.out.println("Uspesno povezivanje na servis rmi://" + host + ":" + port + "/" + service);
    }

    public void onTagMessage(TagMessage msg, String tag) throws RemoteException {
        System.out.println(msg.user.getName());
        System.out.println(msg.msg);
        System.out.println("#" + tag);
    }

    public void sendMessage(TagMessage msg) throws RemoteException {
        this.proxy.sendMessage(msg);
    }

    public void followTag(User user, String tag) throws RemoteException {
        this.proxy.follow(user, tag, this);
    }

    public static void main(String[] args) {
        try {

            TagClient c = new TagClient("127.0.0.1", "1099", "TagMessageService");
            Scanner s = new Scanner(System.in);

            System.out.println("Unesite id i username");
            User u = new UserImpl(Integer.parseInt(s.nextLine()), s.nextLine().trim());

            while (true) {
                System.out.println("***** Glavni Menu *****");
                System.out.println("1 - Posaljite novu poruku");
                System.out.println("2 - Pratite tag");
                System.out.println("3 - Izlaz");

                int temp = Integer.parseInt(s.nextLine().trim());

                if (temp == 3)
                    break;

                else if (temp == 1) {
                    System.out.println("Unesite sadržaj poruke");
                    String content = s.nextLine();

                    System.out.println("Unesite listu tagova i exit za kraj");
                    List<String> tags = new LinkedList<>();
                    String str = s.nextLine().trim();
                    while (!str.equals("exit")) {
                        tags.add(str);
                        str = s.nextLine().trim();
                    }

                    c.sendMessage(new TagMessage(u, content, tags));
                } else if (temp == 2) {
                    System.out.println("Unesite tag koji zelite da pratite");
                    c.followTag(u, s.nextLine().trim());
                } else
                    System.out.println("Unesite broj pored opcije!");
            }

            s.close();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
