using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Jun2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "INLJSC" in both code and config file together.
    [ServiceContract(CallbackContract = typeof(INLJSCCallback), SessionMode = SessionMode.Required)]
    public interface INLJSC
    {
        [OperationContract(IsOneWay = true)]
        void ZapocniIgru(string nadimak);
        [OperationContract(IsOneWay = true)]
        void BaciKocku(string nadimak);
        [OperationContract(IsOneWay = true)]
        void PomeriSe(int x, int y, string nadimak);
    }

    [DataContract]
    public class Igra
    {
        [DataMember]
        public List<Igrac> Igraci { get; set; }
        [DataMember]
        public Igrac Pobednik { get; set; }
        [DataMember]
        public int ID { get; set; }
        public Igra()
        {
            Igraci = new List<Igrac>();
            Pobednik = null;
            ID = -1;
        }
    }

    [DataContract]
    public class Igrac
    {
        [DataMember]
        public string Nadimak { get; set; }
        [DataMember]
        public short X { get; set; }
        [DataMember]
        public short Y { get; set; }
        [DataMember]
        public short Pojeo { get; set; }
        [DataMember]
        public short Pojeden { get; set; }
        [DataMember]
        public short Kocka { get; set; }
        [DataMember]
        public Igra Igra { get; set; }
        [DataMember]
        public INLJSCCallback Callback { get; set; }

        public Igrac()
        {
            Nadimak = "UNKOWN";
            X = Y = Pojeo = Pojeden = Kocka = 0;
            Callback = null;
        }
    }

}
