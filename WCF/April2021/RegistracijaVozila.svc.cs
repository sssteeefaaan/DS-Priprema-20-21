using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace April2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "RegistracijaVozila" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select RegistracijaVozila.svc or RegistracijaVozila.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
    public class RegistracijaVozila : IRegistracijaVozila
    {
        private Dictionary<string, Vlasnik> _vlasnici;

        public RegistracijaVozila()
        {
            _vlasnici = new Dictionary<string, Vlasnik>();
        }

        public void Registracija(Vlasnik vl, Vozilo voz)
        {
            if (vl == null || voz == null)
                return;

            if (!_vlasnici.ContainsKey(vl.JMBG))
                _vlasnici.Add(vl.JMBG, new Vlasnik()
                {
                    Ime = vl.Ime,
                    Prezime = vl.Prezime,
                    JMBG = vl.JMBG,
                    Vozila = new List<Vozilo>()
                });

            // Ovo ce uvek da prodje, s obzirom da je vrednost vlasnika vozila uvek razlicita
            if (!_vlasnici[vl.JMBG].Vozila.Contains(voz))
                _vlasnici[vl.JMBG].Vozila.Add(new Vozilo()
                {
                    Model = voz.Model,
                    Marka = voz.Marka,
                    Boja = voz.Boja,
                    Vlasnik = _vlasnici[vl.JMBG]
                });
        }
        public List<Vozilo> VratiVozila(string jmbg)
        {
            List<Vozilo> ret = new List<Vozilo>();

            if (_vlasnici.ContainsKey(jmbg))
                foreach (Vozilo v in _vlasnici[jmbg].Vozila)
                    ret.Add(v);

            return ret;
        }
        public List<Vlasnik> VratiVlasnikeModela(string model)
        {
            List<Vlasnik> ret = new List<Vlasnik>();

            foreach (Vlasnik vl in _vlasnici.Values)
            {
                foreach (Vozilo v in vl.Vozila)
                {
                    if (v.Model == model)
                    {
                        ret.Add(vl);
                        break;
                    }
                }
            }

            return ret;
        }
        public List<Vozilo> VratiSvaVozila()
        {
            List<Vozilo> ret = new List<Vozilo>();
            foreach (Vlasnik vl in _vlasnici.Values)
                foreach (Vozilo v in vl.Vozila)
                    ret.Add(v);

            return ret;
        }
    }
}
