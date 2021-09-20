using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Oktobar2021
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IEmailService" in both code and config file together.
    [ServiceContract(SessionMode = SessionMode.Required, CallbackContract =typeof(IEmailCallback))]
    public interface IEmailService
    {
        [OperationContract(IsOneWay =true)]
        void Register(string nickname);
        [OperationContract(IsOneWay = true)]
        void SendEmail(Email email);
    }

    [DataContract]
    public class Email
    {
        [DataMember]
        public string Title { get; set; }
        [DataMember]
        public string Text { get; set; }
        [DataMember]
        public string From { get; set; }
        [DataMember]
        public List<string> For { get; set; }

        public Email()
        {
            For = new List<string>();
        }
    }
}
