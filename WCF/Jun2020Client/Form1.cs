using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Jun2020Client.SR;
using System.ServiceModel;

namespace Jun2020Client
{
    public partial class FormLoto : Form, ILotoCallback
    {
        public LotoClient Proxy { get; set; }
        public FormLoto()
        {
            InitializeComponent();
            Proxy = new LotoClient(new InstanceContext(this));
        }

        public void Cestitka(string print)
        {
            MessageBox.Show(this,
                print,
                "POBEDNIK",
                MessageBoxButtons.OK,
                MessageBoxIcon.Information);
        }

        public void Kraj(int petice, int sestice, int sedmince)
        {
            MessageBox.Show(this,
                $"Petice: {petice}\nŠestice: {sestice}\nSedmice: {sedmince}",
                "Kraj",
                MessageBoxButtons.OK,
                MessageBoxIcon.Information);
        }

        public void NoviBroj(int broj)
        {
            listViewDobitnaKombinacija.Items[0].SubItems.Add("" + broj);
        }

        private void bttnDodaj_Click(object sender, EventArgs e)
        {
            if(Validacija())
            {
                Kombinacija k = new Kombinacija()
                {
                    Brojevi = new List<int>() 
                    { 
                        int.Parse(textBoxBroj1.Text),
                        int.Parse(textBoxBroj2.Text),
                        int.Parse(textBoxBroj3.Text),
                        int.Parse(textBoxBroj4.Text),
                        int.Parse(textBoxBroj5.Text),
                        int.Parse(textBoxBroj6.Text),
                        int.Parse(textBoxBroj7.Text),
                    }
                };

                int id = Proxy.NovaKombinacija(textBox1.Text, k);
                if (id != -1)
                {
                    listViewMojeKombinacije.Items.Add(new ListViewItem(new string[]
                    {
                        id.ToString(),
                        textBoxBroj1.Text,
                        textBoxBroj2.Text,
                        textBoxBroj3.Text,
                        textBoxBroj4.Text,
                        textBoxBroj5.Text,
                        textBoxBroj6.Text,
                        textBoxBroj7.Text
                    }));

                    textBox1.Enabled = false;
                }
                else
                    MessageBox.Show(this,
                        "Greška pri prijavljivanju kombinacije",
                        "Greška",
                        MessageBoxButtons.OK,
                        MessageBoxIcon.Error);
            }
        }

        private bool Validacija()
        {
            foreach (Control control in this.Controls)
            {
                if (control is TextBox)
                {
                    if (String.IsNullOrEmpty(control.Text))
                    {
                        MessageBox.Show("Popunite sva polja!");
                        return false;
                    }

                    if(control.Name.Contains("Broj") && (int.Parse(control.Text) < 1 || int.Parse(control.Text) > 40))
                    {
                        MessageBox.Show("Brojevi moraju biti u opsegu [1-40]!");
                        return false;
                    }
                }
            }

            return true;
        }
        private void FormLoto_Load(object sender, EventArgs e)
        {
            listViewMojeKombinacije.GridLines =
                listViewMojeKombinacije.FullRowSelect =
                listViewDobitnaKombinacija.GridLines =
                listViewDobitnaKombinacija.FullRowSelect = true;
            listViewDobitnaKombinacija.View =
                listViewMojeKombinacije.View = View.Details;

            bttnUkloni.Enabled = false;

            for (int i = 0; i < 8; i++)
            {
                listViewDobitnaKombinacija.Columns.Add(new ColumnHeader()
                {
                    Width = (i != 0 ? (listViewDobitnaKombinacija.Width - 3) / 7 : 0),
                    TextAlign = HorizontalAlignment.Center,
                    Text = i != 0 ? "" + i + "." : "ID"
                });
                listViewMojeKombinacije.Columns.Add(new ColumnHeader()
                {
                    Width = (i != 0 ? listViewDobitnaKombinacija.Width / 7 : 0),
                    TextAlign = HorizontalAlignment.Center,
                    Text = i != 0 ? "" + i + "." : "ID"
                });
            }

            listViewDobitnaKombinacija.Items.Add(new ListViewItem()
            {
                Text = ""
            });
        }

        private void bttnUkloni_Click(object sender, EventArgs e)
        {
            int id = int.Parse(listViewMojeKombinacije.SelectedItems[0].SubItems[0].Text);
            if (Proxy.UkloniKombinaciju(textBox1.Text, id))
                listViewMojeKombinacije.Items.Remove(listViewMojeKombinacije.SelectedItems[0]);
            else
                Console.WriteLine("Greška pri uklanjanju kombinacije!");
        }

        private void listViewMojeKombinacije_SelectedIndexChanged(object sender, EventArgs e)
        {
                bttnUkloni.Enabled = listViewMojeKombinacije.SelectedItems.Count == 1;
        }
    }
}
