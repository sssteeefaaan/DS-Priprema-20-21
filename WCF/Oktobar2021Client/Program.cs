using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Oktobar2021;
using Oktobar2021Client.SR;

namespace Oktobar2021Client
{
    public class Program : IEmailServiceCallback
    {
        private EmailServiceClient Proxy { get; set; }
        public Program()
        {
            Proxy = new EmailServiceClient(new System.ServiceModel.InstanceContext(this));
        }
        public void Register(string nickname)
        {
            Proxy.Register(nickname);
        }
        public void SendEmail(string title, string text, List<string> users)
        {
            Proxy.SendEmail(new Email()
            {
                Title = title,
                Text = text,
                For = users
            });
        }
        static void Main(string[] args)
        {
            Program p = new Program();

            Console.Write("Input nickname: ");
            p.Register(Console.ReadLine());


            Console.Write("Input email title: ");
            string title = Console.ReadLine();

            Console.Write("Input email text: ");
            string text = Console.ReadLine();

            Console.Write("Input number of receivers: ");
            int recvNumb = int.Parse(Console.ReadLine());

            List<string> receivers = new List<string>();
            for (int i = 0; i < recvNumb; i++)
            {
                Console.Write($"Input the nickname of the {i+1}. receiver: ");
                receivers.Add(Console.ReadLine());
            }

            p.SendEmail(title, text, receivers);

            Console.WriteLine("Press Enter for exit...");
            Console.ReadLine();
        }

        public void OnEmail(Email email)
        {
            Console.WriteLine($"*** New Email\n\tFrom: {email.From}");
            Console.WriteLine($"\tTittle: {email.Title}");
            Console.WriteLine($"\tText: {email.Text} ");
        }
    }
}
