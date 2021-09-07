using Oktobar2020ClientForm.Reference;
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

namespace Oktobar2020ClientForm
{
    public partial class FormCalculator : Form, ICalculatorCallback
    {
        private CalculatorClient Proxy { get; set; }
        private bool clear;
        public FormCalculator()
        {
            InitializeComponent();
            Proxy = new CalculatorClient(new InstanceContext(this));
            textBoxResult.Text = "0";
            clear = true;
        }

        public void Result(decimal result, string expression)
        {
            textBoxResult.Text = result.ToString();
            textBoxExpression.Text = expression;
        }

        private void bttnClear_Click(object sender, EventArgs e)
        {
            Proxy.Clear();
        }

        private void bttnMultiply_Click(object sender, EventArgs e)
        {
            Proxy.Multiply(decimal.Parse(textBoxResult.Text));
            clear = true;
        }

        private void bttnDivide_Click(object sender, EventArgs e)
        {
            Proxy.Divide(decimal.Parse(textBoxResult.Text));
            clear = true;
        }

        private void bttnSubtract_Click(object sender, EventArgs e)
        {
            Proxy.Subtract(decimal.Parse(textBoxResult.Text));
            clear = true;
        }

        private void bttnAdd_Click(object sender, EventArgs e)
        {
            Proxy.Add(decimal.Parse(textBoxResult.Text));
            clear = true;
        }

        private void bttnEquals_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Not implemented!");
        }

        private void bttn_Click(object sender, EventArgs e)
        {
            ConcatNumber(((Button)sender).Text);
        }

        private void ConcatNumber(string val)
        {
            if (clear) {
                textBoxResult.Text = "";
                clear = false;
            }

            textBoxResult.Text += val;
        }
    }
}
