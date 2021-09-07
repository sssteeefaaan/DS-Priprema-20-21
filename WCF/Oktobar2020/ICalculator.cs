using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Oktobar2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "ICalculator" in both code and config file together.
    [ServiceContract(SessionMode = SessionMode.Required, CallbackContract = typeof(ICalculatorCallback))]
    public interface ICalculator
    {
        [OperationContract(IsOneWay = true)]
        void Clear();
        [OperationContract(IsOneWay = true)]
        void Add(decimal number);
        [OperationContract(IsOneWay = true)]
        void Subtract(decimal number);
        [OperationContract(IsOneWay = true)]
        void Multiply(decimal number);
        [OperationContract(IsOneWay = true)]
        void Divide(decimal number);
    }
}
