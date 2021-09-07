using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Oktobar2020
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Calculator" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Calculator.svc or Calculator.svc.cs at the Solution Explorer and start debugging.
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.PerSession)]
    public class Calculator : ICalculator
    {
        private decimal Acc { get; set; }
        private string Expression { get; set; }
        private ICalculatorCallback Callback { get; set; }
        public Calculator()
        {
            Callback = OperationContext.Current.GetCallbackChannel<ICalculatorCallback>();
            Clear();
        }

        public void Clear()
        {
            Acc = 0;
            Expression = "";
            Callback.Result(Acc, Expression);
        }

        public void Add(decimal op)
        {
            Acc += op;
            if (String.IsNullOrEmpty(Expression))
                Expression = "" + op;
            else
                Expression += " + " + op;

            Callback.Result(Acc, Expression);
        }
        public void Subtract(decimal op)
        {
            Acc -= op;
            if (String.IsNullOrEmpty(Expression))
                Expression = "" + op;
            else
                Expression += " - " + op;

            Callback.Result(Acc, Expression);
        }
        public void Multiply(decimal op)
        {
            Acc *= op;
            if (String.IsNullOrEmpty(Expression))
                Expression = "" + op;
            else
                Expression += " * " + op;

            Callback.Result(Acc, Expression);
        }
        public void Divide(decimal op)
        {
            if (op == 0)
            {
                Acc = 0;
                Expression = "";
                Callback.Result(0, "Error");
                return;
            }

            Acc /= op;
            if (String.IsNullOrEmpty(Expression))
                Expression = "" + op;
            else
                Expression += " / " + op;

            Callback.Result(Acc, Expression);
        }
    }
}
