﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Jun2021ClientForm.SRJun2021 {
    using System.Runtime.Serialization;
    using System;
    
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Runtime.Serialization", "4.0.0.0")]
    [System.Runtime.Serialization.DataContractAttribute(Name="Igra", Namespace="http://schemas.datacontract.org/2004/07/Jun2021")]
    [System.SerializableAttribute()]
    public partial class Igra : object, System.Runtime.Serialization.IExtensibleDataObject, System.ComponentModel.INotifyPropertyChanged {
        
        [System.NonSerializedAttribute()]
        private System.Runtime.Serialization.ExtensionDataObject extensionDataField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private int IDField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private System.Collections.Generic.List<Jun2021ClientForm.SRJun2021.Igrac> IgraciField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private Jun2021ClientForm.SRJun2021.Igrac PobednikField;
        
        [global::System.ComponentModel.BrowsableAttribute(false)]
        public System.Runtime.Serialization.ExtensionDataObject ExtensionData {
            get {
                return this.extensionDataField;
            }
            set {
                this.extensionDataField = value;
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public int ID {
            get {
                return this.IDField;
            }
            set {
                if ((this.IDField.Equals(value) != true)) {
                    this.IDField = value;
                    this.RaisePropertyChanged("ID");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public System.Collections.Generic.List<Jun2021ClientForm.SRJun2021.Igrac> Igraci {
            get {
                return this.IgraciField;
            }
            set {
                if ((object.ReferenceEquals(this.IgraciField, value) != true)) {
                    this.IgraciField = value;
                    this.RaisePropertyChanged("Igraci");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public Jun2021ClientForm.SRJun2021.Igrac Pobednik {
            get {
                return this.PobednikField;
            }
            set {
                if ((object.ReferenceEquals(this.PobednikField, value) != true)) {
                    this.PobednikField = value;
                    this.RaisePropertyChanged("Pobednik");
                }
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Runtime.Serialization", "4.0.0.0")]
    [System.Runtime.Serialization.DataContractAttribute(Name="Igrac", Namespace="http://schemas.datacontract.org/2004/07/Jun2021")]
    [System.SerializableAttribute()]
    [System.Runtime.Serialization.KnownTypeAttribute(typeof(Jun2021ClientForm.SRJun2021.Igra))]
    [System.Runtime.Serialization.KnownTypeAttribute(typeof(System.Collections.Generic.List<Jun2021ClientForm.SRJun2021.Igrac>))]
    public partial class Igrac : object, System.Runtime.Serialization.IExtensibleDataObject, System.ComponentModel.INotifyPropertyChanged {
        
        [System.NonSerializedAttribute()]
        private System.Runtime.Serialization.ExtensionDataObject extensionDataField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private object CallbackField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private Jun2021ClientForm.SRJun2021.Igra IgraField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private short KockaField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private string NadimakField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private short PojedenField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private short PojeoField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private short XField;
        
        [System.Runtime.Serialization.OptionalFieldAttribute()]
        private short YField;
        
        [global::System.ComponentModel.BrowsableAttribute(false)]
        public System.Runtime.Serialization.ExtensionDataObject ExtensionData {
            get {
                return this.extensionDataField;
            }
            set {
                this.extensionDataField = value;
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public object Callback {
            get {
                return this.CallbackField;
            }
            set {
                if ((object.ReferenceEquals(this.CallbackField, value) != true)) {
                    this.CallbackField = value;
                    this.RaisePropertyChanged("Callback");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public Jun2021ClientForm.SRJun2021.Igra Igra {
            get {
                return this.IgraField;
            }
            set {
                if ((object.ReferenceEquals(this.IgraField, value) != true)) {
                    this.IgraField = value;
                    this.RaisePropertyChanged("Igra");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public short Kocka {
            get {
                return this.KockaField;
            }
            set {
                if ((this.KockaField.Equals(value) != true)) {
                    this.KockaField = value;
                    this.RaisePropertyChanged("Kocka");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public string Nadimak {
            get {
                return this.NadimakField;
            }
            set {
                if ((object.ReferenceEquals(this.NadimakField, value) != true)) {
                    this.NadimakField = value;
                    this.RaisePropertyChanged("Nadimak");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public short Pojeden {
            get {
                return this.PojedenField;
            }
            set {
                if ((this.PojedenField.Equals(value) != true)) {
                    this.PojedenField = value;
                    this.RaisePropertyChanged("Pojeden");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public short Pojeo {
            get {
                return this.PojeoField;
            }
            set {
                if ((this.PojeoField.Equals(value) != true)) {
                    this.PojeoField = value;
                    this.RaisePropertyChanged("Pojeo");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public short X {
            get {
                return this.XField;
            }
            set {
                if ((this.XField.Equals(value) != true)) {
                    this.XField = value;
                    this.RaisePropertyChanged("X");
                }
            }
        }
        
        [System.Runtime.Serialization.DataMemberAttribute()]
        public short Y {
            get {
                return this.YField;
            }
            set {
                if ((this.YField.Equals(value) != true)) {
                    this.YField = value;
                    this.RaisePropertyChanged("Y");
                }
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="SRJun2021.INLJSC", CallbackContract=typeof(Jun2021ClientForm.SRJun2021.INLJSCCallback), SessionMode=System.ServiceModel.SessionMode.Required)]
    public interface INLJSC {
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/ZapocniIgru")]
        void ZapocniIgru(string nadimak);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/ZapocniIgru")]
        System.Threading.Tasks.Task ZapocniIgruAsync(string nadimak);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/BaciKocku")]
        void BaciKocku(string nadimak);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/BaciKocku")]
        System.Threading.Tasks.Task BaciKockuAsync(string nadimak);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/PomeriSe")]
        void PomeriSe(int x, int y, string nadimak);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/PomeriSe")]
        System.Threading.Tasks.Task PomeriSeAsync(int x, int y, string nadimak);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface INLJSCCallback {
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/Message")]
        void Message([System.ServiceModel.MessageParameterAttribute(Name="message")] string message1);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/Refresh")]
        void Refresh(short pojeden, short pojeo, short kocka, short x, short y);
        
        [System.ServiceModel.OperationContractAttribute(IsOneWay=true, Action="http://tempuri.org/INLJSC/Statistics")]
        void Statistics(Jun2021ClientForm.SRJun2021.Igra igra);
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface INLJSCChannel : Jun2021ClientForm.SRJun2021.INLJSC, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class NLJSCClient : System.ServiceModel.DuplexClientBase<Jun2021ClientForm.SRJun2021.INLJSC>, Jun2021ClientForm.SRJun2021.INLJSC {
        
        public NLJSCClient(System.ServiceModel.InstanceContext callbackInstance) : 
                base(callbackInstance) {
        }
        
        public NLJSCClient(System.ServiceModel.InstanceContext callbackInstance, string endpointConfigurationName) : 
                base(callbackInstance, endpointConfigurationName) {
        }
        
        public NLJSCClient(System.ServiceModel.InstanceContext callbackInstance, string endpointConfigurationName, string remoteAddress) : 
                base(callbackInstance, endpointConfigurationName, remoteAddress) {
        }
        
        public NLJSCClient(System.ServiceModel.InstanceContext callbackInstance, string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(callbackInstance, endpointConfigurationName, remoteAddress) {
        }
        
        public NLJSCClient(System.ServiceModel.InstanceContext callbackInstance, System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(callbackInstance, binding, remoteAddress) {
        }
        
        public void ZapocniIgru(string nadimak) {
            base.Channel.ZapocniIgru(nadimak);
        }
        
        public System.Threading.Tasks.Task ZapocniIgruAsync(string nadimak) {
            return base.Channel.ZapocniIgruAsync(nadimak);
        }
        
        public void BaciKocku(string nadimak) {
            base.Channel.BaciKocku(nadimak);
        }
        
        public System.Threading.Tasks.Task BaciKockuAsync(string nadimak) {
            return base.Channel.BaciKockuAsync(nadimak);
        }
        
        public void PomeriSe(int x, int y, string nadimak) {
            base.Channel.PomeriSe(x, y, nadimak);
        }
        
        public System.Threading.Tasks.Task PomeriSeAsync(int x, int y, string nadimak) {
            return base.Channel.PomeriSeAsync(x, y, nadimak);
        }
    }
}