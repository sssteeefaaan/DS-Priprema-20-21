using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;

namespace Jun2020
{
    public interface ILotoCallback
    {
        [OperationContract(IsOneWay = true)]
        void NoviBroj(int broj);
        [OperationContract(IsOneWay = true)]
        void Kraj(int petice, int sestice, int sedmince);
        [OperationContract(IsOneWay = true)]
        void Cestitka(string print);
    }
}
