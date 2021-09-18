using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace April2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IRegistracijaVozila" in both code and config file together.
    [ServiceContract]
    public interface IRegistracijaVozila
    {
        [OperationContract]
        void Registracija(Vlasnik vl, Vozilo voz);
        [OperationContract]
        List<Vozilo> VratiVozila(string jmbg);
        [OperationContract]
        List<Vlasnik> VratiVlasnikeModela(string model);
        [OperationContract]
        List<Vozilo> VratiSvaVozila();
    }

    [DataContract(IsReference =true)]
    public class Vlasnik
    {
        [DataMember]
        public string Ime { get; set; }
        [DataMember]
        public string Prezime { get; set; }
        [DataMember]
        public string JMBG { get; set; }

        [DataMember]
        public List<Vozilo> Vozila { get; set; }

        public Vlasnik()
        {
            Vozila = new List<Vozilo>();
        }

        public override bool Equals(object obj)
        {
            return obj is Vlasnik vlasnik &&
                   JMBG == vlasnik.JMBG;
        }
    }

    [DataContract(IsReference = true)]
    public class Vozilo
    {
        [DataMember]
        public string Marka { get; set; }
        [DataMember]
        public string Model { get; set; }
        [DataMember]
        public string Boja { get; set; }
        [DataMember]
        public Vlasnik Vlasnik { get; set; }

        public Vozilo()
        {
            Vlasnik = null;
        }

        public override bool Equals(object obj)
        {
            return obj is Vozilo vozilo &&
                   Marka == vozilo.Marka &&
                   Model == vozilo.Model &&
                   Boja == vozilo.Boja;
        }
    }
}
