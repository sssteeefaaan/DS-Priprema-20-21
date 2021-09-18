using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Jun2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ILoto" in both code and config file together.
    [ServiceContract(SessionMode = SessionMode.Required, CallbackContract =typeof(ILotoCallback))]
    public interface ILoto
    {
        [OperationContract(IsOneWay = false)]
        int NovaKombinacija(string nadimak, Kombinacija k);
        [OperationContract(IsOneWay = false)]
        bool UkloniKombinaciju(string nadimak, int ID);
        [OperationContract(IsOneWay = true)]
        void IzvuciBroj(string adminPass, int broj);
    }

    public class Korisnik
    {
        public string nadimak { get; set; }
        public ILotoCallback Callback { get; set; }
        public Dictionary<int, Kombinacija> Kombinacije { get; set; }
        public Korisnik()
        {
            Kombinacije = new Dictionary<int, Kombinacija>();
        }
    }

    [DataContract]
    public class Kombinacija
    {
        [DataMember]
        public int ID { get; set; }

        [DataMember]
        public List<int> Brojevi { get; set; }

        public Kombinacija()
        {
            Brojevi = new List<int>();
        }
    }
}
