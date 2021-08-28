using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Jun2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "NLJSC" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select NLJSC.svc or NLJSC.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
    public class NLJSC : INLJSC
    {
        private Dictionary<string, Igrac> Igraci { get; set; }
        private Dictionary<int, Igra> Igre { get; set; }

        public NLJSC()
        {
            Igraci = new Dictionary<string, Igrac>();
            Igre = new Dictionary<int, Igra>();
        }
        public void BaciKocku(string nadimak)
        {
            if (!Igraci.ContainsKey(nadimak) || Igraci[nadimak].Igra == null)
            {
                OperationContext.Current.GetCallbackChannel<INLJSCCallback>().Message("Niste zapoceli igru!");
                return;
            }

            if (Igraci[nadimak].Kocka != 0)
            {
                Igraci[nadimak].Callback.Message("Kocka je vec bacena!");
                return;
            }

            Igraci[nadimak].Kocka = (short)(new Random().Next(6) + 1);

            foreach (Igrac i in Igraci[nadimak].Igra.Igraci)
            {
                if (i.Nadimak == nadimak)
                    i.Callback.Refresh(i.Pojeden, i.Pojeo, i.Kocka, i.X, i.Y);
                else
                    i.Callback.Message($"'{nadimak}' je bacio kocku i dobio: {Igraci[nadimak].Kocka}");
            }
        }

        public void PomeriSe(int x, int y, string nadimak)
        {
            if (!Igraci.ContainsKey(nadimak) || Igraci[nadimak].Igra == null)
            {
                OperationContext.Current.GetCallbackChannel<INLJSCCallback>().Message("Niste zapoceli igru!");
                return;
            }

            if (Igraci[nadimak].Kocka < 1 || Igraci[nadimak].Kocka > 6)
            {
                Igraci[nadimak].Callback.Message("Niste bacili kocku!");
                return;
            }

            if (x > 9 || x < 0 ||
                y > 9 || y < 0 ||
                Math.Abs(Igraci[nadimak].X - x) + Math.Abs(Igraci[nadimak].Y - y) > Igraci[nadimak].Kocka)
            {
                Igraci[nadimak].Callback.Message("Nedostizno polje!");
                return;
            }

            Igraci[nadimak].X = (short)x;
            Igraci[nadimak].Y = (short)y;
            Igraci[nadimak].Kocka = 0;

            foreach (Igrac i in Igraci[nadimak].Igra.Igraci)
            {
                if (i.Nadimak != nadimak)
                {
                    i.Callback.Message($"'{nadimak}' se pomerio na polje: ({x}, {y})");
                    if (i.X == x && i.Y == y)
                    {
                        i.Callback.Message($"'{nadimak}' vas je pojeo!");
                        i.Pojeden++;
                        i.Callback.Refresh(i.Pojeden, i.Pojeo, i.Kocka, i.X, i.Y);

                        Igraci[nadimak].Callback.Message($"Pojeli ste '{i.Nadimak}'");
                        Igraci[nadimak].Pojeo++;

                        FindNewPlace(i);
                    }
                }
            }

            Igraci[nadimak].Callback.Refresh(Igraci[nadimak].Pojeden, Igraci[nadimak].Pojeo, Igraci[nadimak].Kocka, Igraci[nadimak].X, Igraci[nadimak].Y);
            CheckGame(Igraci[nadimak].Igra);
        }

        private void CheckGame(Igra igra)
        {
            foreach (Igrac i in igra.Igraci)
                if (i.Pojeo == 10)
                    igra.Pobednik = i;

            if (igra.Pobednik != null)
            {
                foreach (Igrac i in igra.Igraci)
                {
                    if (i.Nadimak != igra.Pobednik.Nadimak)
                        i.Callback.Message($"'{igra.Pobednik.Nadimak}' je pobedio!");
                    else
                        i.Callback.Message("Pobedili ste!");

                    i.Callback.Statistics(igra);

                    i.Igra = null;
                }
            }
        }
        public void ZapocniIgru(string nadimak)
        {
            if (!Igraci.ContainsKey(nadimak))
                Igraci.Add(nadimak, new Igrac() { Nadimak = nadimak });

            Igraci[nadimak].Callback = OperationContext.Current.GetCallbackChannel<INLJSCCallback>();

            if (Igraci[nadimak].Igra != null)
            {
                Igraci[nadimak].Callback.Message("Ne ljuti se covece!");
                return;
            }

            Igra igra = null;
            foreach (Igra i in Igre.Values)
            {
                if (i.Pobednik == null && i.Igraci.Count < 4)
                {
                    igra = i;
                    break;
                }
            }

            if (igra == null)
            {
                Igre.Add(Igre.Count, igra = new Igra()
                {
                    Igraci = new List<Igrac>(),
                    ID = Igre.Count + 1,
                    Pobednik = null
                });
            }

            Igraci[nadimak].Callback.Message($"Pridruzili ste se igri {igra.ID}!");

            foreach(Igrac i in igra.Igraci)
            {
                i.Callback.Message($"'{nadimak} se pridruzio igri!'");
                Igraci[nadimak].Callback.Message($"'{i.Nadimak}' je u igri!");
            }

            igra.Igraci.Add(Igraci[nadimak]);

            Igraci[nadimak].Igra = igra;
            Igraci[nadimak].Kocka =
                Igraci[nadimak].Pojeo =
                Igraci[nadimak].Pojeden = 0;

            FindNewPlace(Igraci[nadimak]);
        }

        private void FindNewPlace(Igrac igrac)
        {
            bool okay;
            do
            {
                okay = true;
                igrac.X = (short)new Random().Next(0, 10);
                igrac.Y = (short)new Random().Next(0, 10);

                foreach (Igrac i in igrac.Igra.Igraci)
                {
                    if (i.Nadimak != igrac.Nadimak && i.X == igrac.X && i.Y == igrac.Y)
                    {
                        okay = false;
                        break;
                    }
                }
            } while (!okay);

            foreach (Igrac i in igrac.Igra.Igraci)
                if (i.Nadimak != igrac.Nadimak)
                    i.Callback.Message($"Igrac '{igrac.Nadimak}' je na poziciji ({igrac.X}, {igrac.Y})");

            igrac.Callback.Refresh(igrac.Pojeden, igrac.Pojeo, igrac.Kocka, igrac.X, igrac.Y);
        }
    }
}
