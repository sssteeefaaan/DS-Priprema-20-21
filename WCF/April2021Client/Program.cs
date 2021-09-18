using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using April2021Client.SR;

namespace April2021Client
{
    class Program
    {
        static void Main(string[] args)
        {
            RegistracijaVozilaClient proxy = new RegistracijaVozilaClient();

            Console.WriteLine("Unesite podakte za registraciju (Ime, Prezime, JMBG, Marku, Model, Boju)");
            proxy.Registracija(new Vlasnik() 
            { 
                Ime = Console.ReadLine().Trim(),
                Prezime = Console.ReadLine().Trim(),
                JMBG = Console.ReadLine().Trim() 
            }, new Vozilo() { 
                Marka = Console.ReadLine().Trim(),
                Model = Console.ReadLine().Trim(),
                Boja = Console.ReadLine().Trim()
            });

            Console.Write("\nUnesite JMBG vlasnika: ");
            IList<Vozilo> temp = proxy.VratiVozila(Console.ReadLine().Trim());

            Console.WriteLine("\nVozila korisnika:");

            foreach (Vozilo v in temp)
            {
                Console.WriteLine($"Marka: {v.Marka}\nModel: {v.Model}\nBoja: {v.Boja}");
                Console.WriteLine($"Vlasnik:\n\tIme: {v.Vlasnik.Ime}\n\tPrezime: {v.Vlasnik.Prezime}\n\tJMBG: {v.Vlasnik.JMBG}\n");
            }

            Console.Write("\nUnesite model vozila: ");
            IList<Vlasnik> temp2 = proxy.VratiVlasnikeModela(Console.ReadLine().Trim());

            Console.WriteLine("\nVlasnici modela:");
            foreach (Vlasnik v in temp2)
                Console.WriteLine($"Ime: {v.Ime}\nPrezime: {v.Prezime}\nJMBG: {v.JMBG}\n");

           temp = proxy.VratiSvaVozila();

            Console.WriteLine("\nSpisak svih vozila u sistemu:");
            foreach (Vozilo v in temp)
            {
                Console.WriteLine($"Marka: {v.Marka}\nModel: {v.Model}\nBoja: {v.Boja}");
                Console.WriteLine($"Vlasnik:\n\tIme: {v.Vlasnik.Ime}\n\tPrezime: {v.Vlasnik.Prezime}\n\tJMBG: {v.Vlasnik.JMBG}\n");
            }

            proxy.Close();
            Console.ReadLine();
        }
    }
}
