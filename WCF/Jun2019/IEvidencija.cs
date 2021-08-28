using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Jun2019
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IEvidencija" in both code and config file together.
    [ServiceContract]
    public interface IEvidencija
    {
        [OperationContract]
        bool Register(string name);
        [OperationContract]
        bool Login(string name);
        [OperationContract]
        bool Logout(string name);
        [OperationContract]
        List<RadniDan> GetDates(string name);
    }

    [DataContract]
    public class RadniDan
    {
        [DataMember]
        public Log Login { get; set; }
        [DataMember]
        public Log Logout { get; set; }
        [DataMember]
        public DateTime Date { get; set; }

        public RadniDan()
        { Date = DateTime.Today; }
    }

    [DataContract]
    public class Log
    {
        [DataMember]
        public DateTime? TimeStamp { get; set; }
        [DataMember]
        public bool Success { get; set; }

        public Log()
        { TimeStamp = DateTime.Now; }
    }

    public class Radnik
    {
        public Dictionary<DateTime, RadniDan> RadniDani { get; set; }
        public string Ime { get; set; }

        public Radnik()
        {
            RadniDani = new Dictionary<DateTime, RadniDan>();
        }
    }
}
