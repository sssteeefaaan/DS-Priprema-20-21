using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Jun2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Loto" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Loto.svc or Loto.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode =InstanceContextMode.Single)]
    public class Loto : ILoto
    {
        private List<int> izvuceniBrojevi;
        private Dictionary<string, Korisnik> korisnici;
        private string adminPass;
        private Random rand;
        public Loto()
        {
            izvuceniBrojevi = new List<int>();
            korisnici = new Dictionary<string, Korisnik>();
            adminPass = "Stefke2021";
            rand = new Random();
        }

        public void IzvuciBroj(string adminPass, int broj)
        {
           if(adminPass == this.adminPass && izvuceniBrojevi.Count < 8)
            {
                // int broj = rand.Next(40) + 1;

                while (izvuceniBrojevi.Contains(broj))
                    broj = rand.Next(40) + 1;

                izvuceniBrojevi.Add(broj);

                foreach (Korisnik k in korisnici.Values)
                    k.Callback.NoviBroj(broj);

                if (izvuceniBrojevi.Count == 7)
                    ProveriRezultate();
            }
        }

        private void ProveriRezultate()
        {
            int petice = 0, sestice = 0, sedmice = 0;
            List<string> pobednici = new List<string>();

            foreach(Korisnik k in korisnici.Values)
            {
                foreach (Kombinacija komb in k.Kombinacije.Values)
                {
                    int pogodjeni = 0;
                    foreach (int i in komb.Brojevi)
                        if (izvuceniBrojevi.Contains(i))
                            pogodjeni++;
                    if (pogodjeni == 5)
                        petice++;
                    else if (pogodjeni == 6)
                        sestice++;
                    else if (pogodjeni == 7)
                    {
                        sedmice++;
                        if (!pobednici.Contains(k.nadimak))
                            pobednici.Add(k.nadimak);
                    }
                }
            }

            foreach(Korisnik k in korisnici.Values)
            {
                k.Callback.Kraj(petice, sestice, sedmice);
                if (pobednici.Contains(k.nadimak))
                    k.Callback.Cestitka("Čestitamo, pobedili ste!");
            }
        }

        public int NovaKombinacija(string nadimak, Kombinacija k)
        {
            if (izvuceniBrojevi.Count > 0)
                return -1;

            if (!korisnici.ContainsKey(nadimak))
                korisnici.Add(nadimak, new Korisnik()
                {
                    nadimak = nadimak,
                    Kombinacije = new Dictionary<int, Kombinacija>(),
                    Callback = OperationContext.Current.GetCallbackChannel<ILotoCallback>()
                });

            if(ProveriKombinaciju(k, korisnici[nadimak]))
            {
                int id = korisnici[nadimak].Kombinacije.Count;
                korisnici[nadimak].Kombinacije.Add(id, new Kombinacija()
                {
                    ID = id,
                    Brojevi = new List<int>(k.Brojevi)
                });

                return id;
            }

            return -1;
        }

        private bool ProveriKombinaciju(Kombinacija k, Korisnik u)
        {
            if (u == null || k == null)
                return false;

            if (k.Brojevi.Count != 7)
                return false;

            foreach (int broj in k.Brojevi)
                if (broj < 1 || broj > 40)
                    return false;

            foreach (Kombinacija komb in u.Kombinacije.Values)
            {
                int identicni = 0;
                foreach (int broj in k.Brojevi)
                if (komb.Brojevi.Contains(broj))
                        identicni++;
                if (identicni == 7)
                    return false;
            }

            return true;
        }

        public bool UkloniKombinaciju(string nadimak, int ID)
        {
            if (izvuceniBrojevi.Count > 0)
                return false;

            if (!korisnici.ContainsKey(nadimak))
                return false;

            if (!korisnici[nadimak].Kombinacije.ContainsKey(ID))
                return false;

            korisnici[nadimak].Kombinacije.Remove(ID);
            return true;
        }
    }
}
