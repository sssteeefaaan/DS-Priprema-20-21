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
using Jun2019ClientForm.Evidencija;

namespace Jun2019ClientForm
{
    [CallbackBehavior(ConcurrencyMode = ConcurrencyMode.Multiple)]
    public partial class Form1 : Form
    {
        private EvidencijaClient proxy;
        private string name;
        public Form1()
        {
            InitializeComponent();
            proxy = new EvidencijaClient();
            label.Text = "Niste ulogovani!";
            name = null;
        }

        private void bttnLogin_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(textBoxName.Text))
            {
                if (String.IsNullOrEmpty(name))
                {
                    MessageBox.Show("Niste registrovani!");
                    return;
                }
            }
            else
                name = textBoxName.Text;

            if (proxy.Login(name))
                MessageBox.Show("Dolazak u " + DateTime.Now.ToShortTimeString());
            else
                MessageBox.Show("Greska!");
        }

        private void bttnLogout_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(textBoxName.Text))
            {
                if (String.IsNullOrEmpty(name))
                {
                    MessageBox.Show("Niste registrovani!");
                    return;
                }
            }
            else
                name = textBoxName.Text;

            if (proxy.Logout(name))
                MessageBox.Show("Odlazak u " + DateTime.Now.ToShortTimeString());
            else
                MessageBox.Show("Greska!");
        }

        private void bttnReg_Click(object sender, EventArgs e)
        {
            if (String.IsNullOrEmpty(textBoxName.Text))
            {
                MessageBox.Show("Unesite svoje ime!");
                return;
            }

            name = textBoxName.Text;

            if (proxy.Register(textBoxName.Text))
                label.Text = "Ulogovani kao '" + name + "'";
            else
            {
                label.Text = "Niste ulogovani!";
                name = null;
            }
        }

        private void bttnEvidencija_Click(object sender, EventArgs e)
        {
            if(String.IsNullOrEmpty(name))
            {
                MessageBox.Show("Niste registrovani!");
                return;
            }

            IList<RadniDan> dani = proxy.GetDates(name);

            string msg = "***** Evidencija *****\n";
            foreach(RadniDan rd in dani)
            {
                msg += "Dan; " + rd.Date.ToLongDateString() + "\n";
                msg += "Dolazak: " + rd.Login?.TimeStamp?.ToShortTimeString() + " " + (rd.Login.Success ? "uspesno" : "greska") + "\n";
                msg += "Odlazak: " + rd.Logout?.TimeStamp?.ToShortTimeString() + " " + (rd.Logout.Success ? "uspesno" : "greska") + "\n";
                msg += "*******************" + "\n";
            }

            MessageBox.Show(msg);
        }
    }
}
