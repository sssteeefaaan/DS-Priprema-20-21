using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace Oktobar2020
{
    public interface ICalculatorCallback
    {
        [OperationContract(IsOneWay = true)]
        void Result(decimal result, string expression);
    }
}
