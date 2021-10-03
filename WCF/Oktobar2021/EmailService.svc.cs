using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Oktobar2021
{
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.PerSession)]
    public class EmailService : IEmailService
    {
        private static Dictionary<string, List<IEmailCallback>> Users
        {
            get
            {
                if (_users == null)
                    _users = new Dictionary<string, List<IEmailCallback>>();
                return _users;
            }
        }

        private static Dictionary<string, List<IEmailCallback>> _users;

        private string Nickname { get; set; }

        public EmailService()
        {
            Nickname = null;
        }
        public void Register(string nickname)
        {
            if (!Users.ContainsKey(nickname))
                Users.Add(nickname, new List<IEmailCallback>());

            if(!Users[nickname].Contains(OperationContext.Current.GetCallbackChannel<IEmailCallback>()))
                Users[nickname].Add(OperationContext.Current.GetCallbackChannel<IEmailCallback>());

            Nickname = nickname;
        }

        public void SendEmail(Email email)
        {
            email.From = Nickname;
            foreach(string s in email.For)
            {
                if(Users.ContainsKey(s))
                {
                    foreach(IEmailCallback cb in Users[s])
                    {
                        if (cb != null)
                            cb.OnEmail(email);
                    }
                }
            }
        }
    }
}
