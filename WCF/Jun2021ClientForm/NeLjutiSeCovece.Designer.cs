
namespace Jun2021ClientForm
{
    partial class NeLjutiSeCovece
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
            this.bttnZapocniIgru = new System.Windows.Forms.Button();
            this.bttnPomeriSe = new System.Windows.Forms.Button();
            this.bttnBaciKocku = new System.Windows.Forms.Button();
            this.textBoxNadimak = new System.Windows.Forms.TextBox();
            this.numericUpDownX = new System.Windows.Forms.NumericUpDown();
            this.numericUpDownY = new System.Windows.Forms.NumericUpDown();
            this.lblKocka = new System.Windows.Forms.Label();
            this.lblX = new System.Windows.Forms.Label();
            this.lblY = new System.Windows.Forms.Label();
            this.lblPojeo = new System.Windows.Forms.Label();
            this.lblPojeden = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownX)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownY)).BeginInit();
            this.SuspendLayout();
            // 
            // bttnZapocniIgru
            // 
            this.bttnZapocniIgru.Location = new System.Drawing.Point(311, 12);
            this.bttnZapocniIgru.Name = "bttnZapocniIgru";
            this.bttnZapocniIgru.Size = new System.Drawing.Size(146, 32);
            this.bttnZapocniIgru.TabIndex = 0;
            this.bttnZapocniIgru.Text = "Započni Igru";
            this.bttnZapocniIgru.UseVisualStyleBackColor = true;
            this.bttnZapocniIgru.Click += new System.EventHandler(this.bttnZapocniIgru_Click);
            // 
            // bttnPomeriSe
            // 
            this.bttnPomeriSe.Location = new System.Drawing.Point(27, 105);
            this.bttnPomeriSe.Name = "bttnPomeriSe";
            this.bttnPomeriSe.Size = new System.Drawing.Size(179, 41);
            this.bttnPomeriSe.TabIndex = 1;
            this.bttnPomeriSe.Text = "Pomeri se";
            this.bttnPomeriSe.UseVisualStyleBackColor = true;
            this.bttnPomeriSe.Click += new System.EventHandler(this.bttnPomeriSe_Click);
            // 
            // bttnBaciKocku
            // 
            this.bttnBaciKocku.Location = new System.Drawing.Point(268, 105);
            this.bttnBaciKocku.Name = "bttnBaciKocku";
            this.bttnBaciKocku.Size = new System.Drawing.Size(168, 41);
            this.bttnBaciKocku.TabIndex = 2;
            this.bttnBaciKocku.Text = "Baci kocku";
            this.bttnBaciKocku.UseVisualStyleBackColor = true;
            this.bttnBaciKocku.Click += new System.EventHandler(this.bttnBaciKocku_Click);
            // 
            // textBoxNadimak
            // 
            this.textBoxNadimak.Location = new System.Drawing.Point(12, 12);
            this.textBoxNadimak.Name = "textBoxNadimak";
            this.textBoxNadimak.Size = new System.Drawing.Size(293, 32);
            this.textBoxNadimak.TabIndex = 3;
            // 
            // numericUpDownX
            // 
            this.numericUpDownX.Location = new System.Drawing.Point(27, 152);
            this.numericUpDownX.Name = "numericUpDownX";
            this.numericUpDownX.Size = new System.Drawing.Size(85, 32);
            this.numericUpDownX.TabIndex = 4;
            // 
            // numericUpDownY
            // 
            this.numericUpDownY.Location = new System.Drawing.Point(118, 152);
            this.numericUpDownY.Name = "numericUpDownY";
            this.numericUpDownY.Size = new System.Drawing.Size(88, 32);
            this.numericUpDownY.TabIndex = 5;
            // 
            // lblKocka
            // 
            this.lblKocka.AutoSize = true;
            this.lblKocka.Location = new System.Drawing.Point(300, 154);
            this.lblKocka.Name = "lblKocka";
            this.lblKocka.Size = new System.Drawing.Size(86, 23);
            this.lblKocka.TabIndex = 6;
            this.lblKocka.Text = "Kocka: 0";
            // 
            // lblX
            // 
            this.lblX.AutoSize = true;
            this.lblX.Location = new System.Drawing.Point(37, 56);
            this.lblX.Name = "lblX";
            this.lblX.Size = new System.Drawing.Size(35, 23);
            this.lblX.TabIndex = 7;
            this.lblX.Text = "X: ";
            // 
            // lblY
            // 
            this.lblY.AutoSize = true;
            this.lblY.Location = new System.Drawing.Point(37, 79);
            this.lblY.Name = "lblY";
            this.lblY.Size = new System.Drawing.Size(34, 23);
            this.lblY.TabIndex = 8;
            this.lblY.Text = "Y: ";
            // 
            // lblPojeo
            // 
            this.lblPojeo.AutoSize = true;
            this.lblPojeo.Location = new System.Drawing.Point(264, 56);
            this.lblPojeo.Name = "lblPojeo";
            this.lblPojeo.Size = new System.Drawing.Size(70, 23);
            this.lblPojeo.TabIndex = 9;
            this.lblPojeo.Text = "Pojeo: ";
            // 
            // lblPojeden
            // 
            this.lblPojeden.AutoSize = true;
            this.lblPojeden.Location = new System.Drawing.Point(264, 79);
            this.lblPojeden.Name = "lblPojeden";
            this.lblPojeden.Size = new System.Drawing.Size(88, 23);
            this.lblPojeden.TabIndex = 10;
            this.lblPojeden.Text = "Pojeden: ";
            // 
            // NeLjutiSeCovece
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 23F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(496, 214);
            this.Controls.Add(this.lblPojeden);
            this.Controls.Add(this.lblPojeo);
            this.Controls.Add(this.lblY);
            this.Controls.Add(this.lblX);
            this.Controls.Add(this.lblKocka);
            this.Controls.Add(this.numericUpDownY);
            this.Controls.Add(this.numericUpDownX);
            this.Controls.Add(this.textBoxNadimak);
            this.Controls.Add(this.bttnBaciKocku);
            this.Controls.Add(this.bttnPomeriSe);
            this.Controls.Add(this.bttnZapocniIgru);
            this.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(6, 5, 6, 5);
            this.Name = "NeLjutiSeCovece";
            this.Text = "Ne ljuti se čoveče";
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownX)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDownY)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button bttnZapocniIgru;
        private System.Windows.Forms.Button bttnPomeriSe;
        private System.Windows.Forms.Button bttnBaciKocku;
        private System.Windows.Forms.TextBox textBoxNadimak;
        private System.Windows.Forms.NumericUpDown numericUpDownX;
        private System.Windows.Forms.NumericUpDown numericUpDownY;
        private System.Windows.Forms.Label lblKocka;
        private System.Windows.Forms.Label lblX;
        private System.Windows.Forms.Label lblY;
        private System.Windows.Forms.Label lblPojeo;
        private System.Windows.Forms.Label lblPojeden;
    }
}

