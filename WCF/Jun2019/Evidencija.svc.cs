using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Jun2019
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Evidencija" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Evidencija.svc or Evidencija.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single, ConcurrencyMode = ConcurrencyMode.Multiple)]
    public class Evidencija : IEvidencija
    {
        public Dictionary<string, Radnik> Radnici { get; set; }
        public Evidencija()
        {
            Radnici = new Dictionary<string, Radnik>();
        }

        public List<RadniDan> GetDates(string name)
        {
            List<RadniDan> ret = new List<RadniDan>();
            if (Radnici.ContainsKey(name))
            {
                foreach (RadniDan rd in Radnici[name].RadniDani.Values)
                {
                    if (rd.Logout != null)
                        ret.Add(rd);
                }
            }

            return ret;
        }

        public bool Login(string name)
        {
            if (!Radnici.ContainsKey(name))
                return false;

            if (Radnici[name].RadniDani.ContainsKey(DateTime.Today))
                return false;

            RadniDan rd = new RadniDan();
            rd.Login = new Log()
            {
                TimeStamp = DateTime.Now,
                Success = true
            };
            rd.Logout = new Log()
            {
                TimeStamp = null,
                Success = false
            };

            Radnici[name].RadniDani.Add(DateTime.Today, rd);

            return true;
        }
        public bool Logout(string name)
        {
            if (!Radnici.ContainsKey(name))
                return false;

            if (!Radnici[name].RadniDani.ContainsKey(DateTime.Today))
            {
                Radnici[name].RadniDani.Add(DateTime.Today, new RadniDan()
                {
                    Login = new Log()
                    {
                        TimeStamp = null,
                        Success = false
                    },
                Logout = new Log()
                    {
                        TimeStamp = DateTime.Now,
                        Success = false
                    }
                });
                return false;
            }

            if (Radnici[name].RadniDani[DateTime.Today].Logout?.TimeStamp != null)
                return false;

            Radnici[name].RadniDani[DateTime.Today].Logout.TimeStamp = DateTime.Now;
            Radnici[name].RadniDani[DateTime.Today].Logout.Success = true;

            return true;
        }

        public bool Register(string name)
        {
            if (Radnici.ContainsKey(name))
                return false;

            Radnici.Add(name, new Radnik()
            {
                Ime = name,
                RadniDani = new Dictionary<DateTime, RadniDan>()
            });

            return true;
        }
    }
}
