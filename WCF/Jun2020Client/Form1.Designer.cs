
namespace Jun2020Client
{
    partial class FormLoto
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.bttnUkloni = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.lblNadimak = new System.Windows.Forms.Label();
            this.bttnDodaj = new System.Windows.Forms.Button();
            this.listViewDobitnaKombinacija = new System.Windows.Forms.ListView();
            this.listViewMojeKombinacije = new System.Windows.Forms.ListView();
            this.textBoxBroj1 = new System.Windows.Forms.TextBox();
            this.textBoxBroj4 = new System.Windows.Forms.TextBox();
            this.textBoxBroj3 = new System.Windows.Forms.TextBox();
            this.textBoxBroj2 = new System.Windows.Forms.TextBox();
            this.textBoxBroj5 = new System.Windows.Forms.TextBox();
            this.textBoxBroj6 = new System.Windows.Forms.TextBox();
            this.textBoxBroj7 = new System.Windows.Forms.TextBox();
            this.groupBoxNovaKombinacija = new System.Windows.Forms.GroupBox();
            this.groupBoxMojeKombinacije = new System.Windows.Forms.GroupBox();
            this.groupBoxDobitnaKombinacija = new System.Windows.Forms.GroupBox();
            this.groupBoxNovaKombinacija.SuspendLayout();
            this.groupBoxMojeKombinacije.SuspendLayout();
            this.groupBoxDobitnaKombinacija.SuspendLayout();
            this.SuspendLayout();
            // 
            // bttnUkloni
            // 
            this.bttnUkloni.Location = new System.Drawing.Point(478, 156);
            this.bttnUkloni.Name = "bttnUkloni";
            this.bttnUkloni.Size = new System.Drawing.Size(113, 30);
            this.bttnUkloni.TabIndex = 0;
            this.bttnUkloni.Text = "Ukloni";
            this.bttnUkloni.UseVisualStyleBackColor = true;
            this.bttnUkloni.Click += new System.EventHandler(this.bttnUkloni_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(163, 119);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(355, 32);
            this.textBox1.TabIndex = 1;
            // 
            // lblNadimak
            // 
            this.lblNadimak.AutoSize = true;
            this.lblNadimak.Location = new System.Drawing.Point(66, 122);
            this.lblNadimak.Name = "lblNadimak";
            this.lblNadimak.Size = new System.Drawing.Size(91, 23);
            this.lblNadimak.TabIndex = 2;
            this.lblNadimak.Text = "Nadimak:";
            // 
            // bttnDodaj
            // 
            this.bttnDodaj.Location = new System.Drawing.Point(235, 70);
            this.bttnDodaj.Name = "bttnDodaj";
            this.bttnDodaj.Size = new System.Drawing.Size(113, 30);
            this.bttnDodaj.TabIndex = 3;
            this.bttnDodaj.Text = "Dodaj";
            this.bttnDodaj.UseVisualStyleBackColor = true;
            this.bttnDodaj.Click += new System.EventHandler(this.bttnDodaj_Click);
            // 
            // listViewDobitnaKombinacija
            // 
            this.listViewDobitnaKombinacija.FullRowSelect = true;
            this.listViewDobitnaKombinacija.GridLines = true;
            this.listViewDobitnaKombinacija.HideSelection = false;
            this.listViewDobitnaKombinacija.Location = new System.Drawing.Point(6, 31);
            this.listViewDobitnaKombinacija.Name = "listViewDobitnaKombinacija";
            this.listViewDobitnaKombinacija.Size = new System.Drawing.Size(585, 60);
            this.listViewDobitnaKombinacija.TabIndex = 8;
            this.listViewDobitnaKombinacija.UseCompatibleStateImageBehavior = false;
            this.listViewDobitnaKombinacija.View = System.Windows.Forms.View.Details;
            // 
            // listViewMojeKombinacije
            // 
            this.listViewMojeKombinacije.FullRowSelect = true;
            this.listViewMojeKombinacije.GridLines = true;
            this.listViewMojeKombinacije.HideSelection = false;
            this.listViewMojeKombinacije.Location = new System.Drawing.Point(6, 31);
            this.listViewMojeKombinacije.Name = "listViewMojeKombinacije";
            this.listViewMojeKombinacije.Size = new System.Drawing.Size(585, 119);
            this.listViewMojeKombinacije.TabIndex = 9;
            this.listViewMojeKombinacije.UseCompatibleStateImageBehavior = false;
            this.listViewMojeKombinacije.SelectedIndexChanged += new System.EventHandler(this.listViewMojeKombinacije_SelectedIndexChanged);
            // 
            // textBoxBroj1
            // 
            this.textBoxBroj1.Location = new System.Drawing.Point(27, 32);
            this.textBoxBroj1.Name = "textBoxBroj1";
            this.textBoxBroj1.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj1.TabIndex = 11;
            // 
            // textBoxBroj4
            // 
            this.textBoxBroj4.Location = new System.Drawing.Point(281, 32);
            this.textBoxBroj4.Name = "textBoxBroj4";
            this.textBoxBroj4.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj4.TabIndex = 17;
            // 
            // textBoxBroj3
            // 
            this.textBoxBroj3.Location = new System.Drawing.Point(197, 32);
            this.textBoxBroj3.Name = "textBoxBroj3";
            this.textBoxBroj3.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj3.TabIndex = 18;
            // 
            // textBoxBroj2
            // 
            this.textBoxBroj2.Location = new System.Drawing.Point(113, 32);
            this.textBoxBroj2.Name = "textBoxBroj2";
            this.textBoxBroj2.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj2.TabIndex = 19;
            // 
            // textBoxBroj5
            // 
            this.textBoxBroj5.Location = new System.Drawing.Point(365, 32);
            this.textBoxBroj5.Name = "textBoxBroj5";
            this.textBoxBroj5.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj5.TabIndex = 20;
            // 
            // textBoxBroj6
            // 
            this.textBoxBroj6.Location = new System.Drawing.Point(450, 32);
            this.textBoxBroj6.Name = "textBoxBroj6";
            this.textBoxBroj6.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj6.TabIndex = 21;
            // 
            // textBoxBroj7
            // 
            this.textBoxBroj7.Location = new System.Drawing.Point(538, 32);
            this.textBoxBroj7.Name = "textBoxBroj7";
            this.textBoxBroj7.Size = new System.Drawing.Size(32, 32);
            this.textBoxBroj7.TabIndex = 22;
            // 
            // groupBoxNovaKombinacija
            // 
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj7);
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj1);
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj6);
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj4);
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj5);
            this.groupBoxNovaKombinacija.Controls.Add(this.bttnDodaj);
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj3);
            this.groupBoxNovaKombinacija.Controls.Add(this.textBoxBroj2);
            this.groupBoxNovaKombinacija.Location = new System.Drawing.Point(12, 157);
            this.groupBoxNovaKombinacija.Name = "groupBoxNovaKombinacija";
            this.groupBoxNovaKombinacija.Size = new System.Drawing.Size(597, 106);
            this.groupBoxNovaKombinacija.TabIndex = 23;
            this.groupBoxNovaKombinacija.TabStop = false;
            this.groupBoxNovaKombinacija.Text = "Nova kombinacija";
            // 
            // groupBoxMojeKombinacije
            // 
            this.groupBoxMojeKombinacije.Controls.Add(this.listViewMojeKombinacije);
            this.groupBoxMojeKombinacije.Controls.Add(this.bttnUkloni);
            this.groupBoxMojeKombinacije.Location = new System.Drawing.Point(12, 269);
            this.groupBoxMojeKombinacije.Name = "groupBoxMojeKombinacije";
            this.groupBoxMojeKombinacije.Size = new System.Drawing.Size(597, 192);
            this.groupBoxMojeKombinacije.TabIndex = 24;
            this.groupBoxMojeKombinacije.TabStop = false;
            this.groupBoxMojeKombinacije.Text = "Moje kombinacije:";
            // 
            // groupBoxDobitnaKombinacija
            // 
            this.groupBoxDobitnaKombinacija.Controls.Add(this.listViewDobitnaKombinacija);
            this.groupBoxDobitnaKombinacija.Location = new System.Drawing.Point(12, 12);
            this.groupBoxDobitnaKombinacija.Name = "groupBoxDobitnaKombinacija";
            this.groupBoxDobitnaKombinacija.Size = new System.Drawing.Size(597, 101);
            this.groupBoxDobitnaKombinacija.TabIndex = 25;
            this.groupBoxDobitnaKombinacija.TabStop = false;
            this.groupBoxDobitnaKombinacija.Text = "Dobitna kombinacija:";
            // 
            // FormLoto
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 23F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(620, 471);
            this.Controls.Add(this.groupBoxDobitnaKombinacija);
            this.Controls.Add(this.groupBoxMojeKombinacije);
            this.Controls.Add(this.groupBoxNovaKombinacija);
            this.Controls.Add(this.lblNadimak);
            this.Controls.Add(this.textBox1);
            this.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(6, 6, 6, 6);
            this.Name = "FormLoto";
            this.Text = "Loto";
            this.Load += new System.EventHandler(this.FormLoto_Load);
            this.groupBoxNovaKombinacija.ResumeLayout(false);
            this.groupBoxNovaKombinacija.PerformLayout();
            this.groupBoxMojeKombinacije.ResumeLayout(false);
            this.groupBoxDobitnaKombinacija.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button bttnUkloni;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label lblNadimak;
        private System.Windows.Forms.Button bttnDodaj;
        private System.Windows.Forms.ListView listViewDobitnaKombinacija;
        private System.Windows.Forms.ListView listViewMojeKombinacije;
        private System.Windows.Forms.TextBox textBoxBroj1;
        private System.Windows.Forms.TextBox textBoxBroj4;
        private System.Windows.Forms.TextBox textBoxBroj3;
        private System.Windows.Forms.TextBox textBoxBroj2;
        private System.Windows.Forms.TextBox textBoxBroj5;
        private System.Windows.Forms.TextBox textBoxBroj6;
        private System.Windows.Forms.TextBox textBoxBroj7;
        private System.Windows.Forms.GroupBox groupBoxNovaKombinacija;
        private System.Windows.Forms.GroupBox groupBoxMojeKombinacije;
        private System.Windows.Forms.GroupBox groupBoxDobitnaKombinacija;
    }
}

