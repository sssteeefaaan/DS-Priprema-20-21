using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Jun2021ClientForm.SRJun2021;

namespace Jun2021ClientForm
{
    public partial class NeLjutiSeCovece : Form, INLJSCCallback
    {
        private NLJSCClient Proxy { get; set; }
        public NeLjutiSeCovece()
        {
            InitializeComponent();
            Proxy = new NLJSCClient(new InstanceContext(this));
        }

        public void Message(string message)
        {
            MessageBox.Show(this, message, "Obaveštenje", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        public void Statistics(Igra igra)
        {
            string stats = "Statistika:\n";
            stats += $"\tPobednik: {igra.Pobednik.Nadimak}\n";
            stats += $"\t\tPojeden: {igra.Pobednik.Pojeden}\n";
            stats += $"\t\tPojeo: {igra.Pobednik.Pojeo}\n";

            foreach(Igrac i in igra.Igraci)
            {
                if (i.Nadimak != igra.Pobednik.Nadimak)
                {
                    stats += $"\tIgrac '{i.Nadimak}'\n";
                    stats += $"\t\tPojeden: {i.Pojeden}\n";
                    stats += $"\t\tPojeo: {i.Pojeo}\n";
                }
            }

            MessageBox.Show(this, stats, "Obaveštenje", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void bttnZapocniIgru_Click(object sender, EventArgs e)
        {
            Proxy.ZapocniIgru(textBoxNadimak.Text);
        }

        private void bttnPomeriSe_Click(object sender, EventArgs e)
        {
            Proxy.PomeriSe((int)numericUpDownX.Value, (int)numericUpDownY.Value, textBoxNadimak.Text);
        }

        private void bttnBaciKocku_Click(object sender, EventArgs e)
        {
            Proxy.BaciKocku(textBoxNadimak.Text);
        }

        public void Refresh(short pojeden, short pojeo, short kocka, short x, short y)
        {
            lblPojeden.Text = "Pojeden: " + pojeden;
            lblPojeo.Text = "Pojeo: " + pojeo;
            lblKocka.Text = "Kocka: " + kocka;
            lblX.Text = "X: " + x;
            lblY.Text = "Y: " + y;
        }
    }
}
