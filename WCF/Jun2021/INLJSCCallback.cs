using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace Jun2021
{
    public interface INLJSCCallback
    {
        [OperationContract(IsOneWay = true)]
        void Message(string message);
        [OperationContract(IsOneWay = true)]
        //void Refresh(Igrac igrac);
        void Refresh(short pojeden, short pojeo, short kocka, short x, short y);
        [OperationContract(IsOneWay = true)]
        void Statistics(Igra igra);
    }
}
