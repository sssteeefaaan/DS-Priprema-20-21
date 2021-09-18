using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Jun2020Control.SR;

namespace Jun2020Control
{
    class Program:ILotoCallback
    {
        static void Main(string[] args)
        {
            LotoClient proxy = new LotoClient(new System.ServiceModel.InstanceContext(new Program()));

            for(int i = 0; i < 7;i++)
            {
                Console.Write($"Unesite {i+1}. broj: ");
                proxy.IzvuciBroj("Stefke2021", int.Parse(Console.ReadLine().Trim()));
            }
        }

        public void Cestitka(string print)
        {
            throw new NotImplementedException();
        }

        public void Kraj(int petice, int sestice, int sedmince)
        {
            throw new NotImplementedException();
        }

        public void NoviBroj(int broj)
        {
            throw new NotImplementedException();
        }
    }
}
