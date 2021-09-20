using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace Oktobar2021
{
    public interface IEmailCallback
    {
        [OperationContract(IsOneWay = true)]
        void OnEmail(Email email);
    }
}
