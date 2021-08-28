
namespace Jun2019ClientForm
{
    partial class Form1
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
            this.bttnReg = new System.Windows.Forms.Button();
            this.bttnLogin = new System.Windows.Forms.Button();
            this.bttnLogout = new System.Windows.Forms.Button();
            this.textBoxName = new System.Windows.Forms.TextBox();
            this.label = new System.Windows.Forms.Label();
            this.bttnEvidencija = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // bttnReg
            // 
            this.bttnReg.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttnReg.Location = new System.Drawing.Point(339, 231);
            this.bttnReg.Margin = new System.Windows.Forms.Padding(6, 5, 6, 5);
            this.bttnReg.Name = "bttnReg";
            this.bttnReg.Size = new System.Drawing.Size(150, 41);
            this.bttnReg.TabIndex = 0;
            this.bttnReg.Text = "Registracija";
            this.bttnReg.UseVisualStyleBackColor = true;
            this.bttnReg.Click += new System.EventHandler(this.bttnReg_Click);
            // 
            // bttnLogin
            // 
            this.bttnLogin.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttnLogin.Location = new System.Drawing.Point(15, 231);
            this.bttnLogin.Margin = new System.Windows.Forms.Padding(6, 5, 6, 5);
            this.bttnLogin.Name = "bttnLogin";
            this.bttnLogin.Size = new System.Drawing.Size(150, 41);
            this.bttnLogin.TabIndex = 1;
            this.bttnLogin.Text = "Login";
            this.bttnLogin.UseVisualStyleBackColor = true;
            this.bttnLogin.Click += new System.EventHandler(this.bttnLogin_Click);
            // 
            // bttnLogout
            // 
            this.bttnLogout.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.bttnLogout.Location = new System.Drawing.Point(177, 231);
            this.bttnLogout.Margin = new System.Windows.Forms.Padding(6, 5, 6, 5);
            this.bttnLogout.Name = "bttnLogout";
            this.bttnLogout.Size = new System.Drawing.Size(150, 41);
            this.bttnLogout.TabIndex = 2;
            this.bttnLogout.Text = "Logout";
            this.bttnLogout.UseVisualStyleBackColor = true;
            this.bttnLogout.Click += new System.EventHandler(this.bttnLogout_Click);
            // 
            // textBoxName
            // 
            this.textBoxName.Location = new System.Drawing.Point(93, 109);
            this.textBoxName.Name = "textBoxName";
            this.textBoxName.Size = new System.Drawing.Size(300, 32);
            this.textBoxName.TabIndex = 3;
            this.textBoxName.Text = "Unesite ime";
            // 
            // label
            // 
            this.label.AutoSize = true;
            this.label.Location = new System.Drawing.Point(79, 18);
            this.label.Name = "label";
            this.label.Size = new System.Drawing.Size(0, 23);
            this.label.TabIndex = 4;
            // 
            // bttnEvidencija
            // 
            this.bttnEvidencija.Location = new System.Drawing.Point(341, 12);
            this.bttnEvidencija.Name = "bttnEvidencija";
            this.bttnEvidencija.Size = new System.Drawing.Size(153, 41);
            this.bttnEvidencija.TabIndex = 5;
            this.bttnEvidencija.Text = "Evidencija";
            this.bttnEvidencija.UseVisualStyleBackColor = true;
            this.bttnEvidencija.Click += new System.EventHandler(this.bttnEvidencija_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 23F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(506, 281);
            this.Controls.Add(this.bttnEvidencija);
            this.Controls.Add(this.label);
            this.Controls.Add(this.textBoxName);
            this.Controls.Add(this.bttnLogout);
            this.Controls.Add(this.bttnLogin);
            this.Controls.Add(this.bttnReg);
            this.Font = new System.Drawing.Font("Times New Roman", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(6, 5, 6, 5);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button bttnReg;
        private System.Windows.Forms.Button bttnLogin;
        private System.Windows.Forms.Button bttnLogout;
        private System.Windows.Forms.TextBox textBoxName;
        private System.Windows.Forms.Label label;
        private System.Windows.Forms.Button bttnEvidencija;
    }
}

