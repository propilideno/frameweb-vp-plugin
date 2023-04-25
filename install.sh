#!/bin/bash

function get_VP_App_Path(){
    #App Default Path
    local VISUAL_PARADIGM_APP_DIR_WINDOWS="C:\\Program Files\\Visual Paradigm CE 17.0\\"
    local VISUAL_PARADIGM_APP_DIR_MAC="/Applications/Visual Paradigm.app"
    local VISUAL_PARADIGM_APP_DIR_LINUX=""
    if [[ "$OSTYPE" == "darwin"* ]]; then
        echo $VISUAL_PARADIGM_APP_DIR_MAC
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        echo $VISUAL_PARADIGM_APP_DIR_LINUX
    elif [[ "$OSTYPE" == "cygwin" ]]; then
        echo $VISUAL_PARADIGM_APP_DIR_WINDOWS
    else
        return 0 # Operating System not supported
    fi
}

function get_VP_Plugin_Path(){
    #Plugin Default Path
    local VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS="C:\\Users\\%USERNAME%\\AppData\\Roaming\\VisualParadigm\\plugins\\"
    local VISUAL_PARADIGM_PLUGIN_DIR_MAC="/Users/$USER/Library/Application Support/VisualParadigm/plugins/"
    local VISUAL_PARADIGM_PLUGIN_DIR_LINUX="/home/$USER/.config/VisualParadigm/plugins/"
    if [[ "$OSTYPE" == "darwin"* ]]; then
        echo $VISUAL_PARADIGM_PLUGIN_DIR_MAC
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        echo $VISUAL_PARADIGM_PLUGIN_DIR_LINUX
    elif [[ "$OSTYPE" == "cygwin" ]]; then
        echo $VISUAL_PARADIGM_PLUGIN_DIR_WINDOWS
    else
        return 0 # Operating System not supported
    fi
}

# If the install fails, then print an error and exit.
function install_fail() {
    echo "Installation failed" 
    exit 1 
} 

# This is the help fuction. It helps users withe the options
function Help(){ 
    echo "Usage: ./install.sh" 
    echo "Make sure u have permission to execute install.sh"
    echo "If u had problems try it: "chmod +x install.sh" or allow execution in properties"
}

function install_maven(){
    if command -v mvn &> /dev/null; then
        echo "Maven already installed."
    else
        # Instalação de dependências
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # Instalação no Mac
            brew install maven
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            # Instalação no Linux
            sudo apt-get install maven
        elif [[ "$OSTYPE" == "cygwin" ]]; then
            # Instalação no Windows usando Cygwin
            echo "Not supported yet"
            exit 1
        else
            echo "Sistema operacional não suportado"
            exit 1
        fi
    fi
}

function install_jdk(){
    if command -v javac &> /dev/null; then
        echo "JDK already installed."
    else
        # Instalação de dependências
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # Instalação no Mac
            brew install java
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            # Instalação no Linux
            sudo apt-get install default-jdk
        elif [[ "$OSTYPE" == "cygwin" ]]; then
            # Instalação no Windows
            echo "Not supported yet"
            exit 1
        else
            echo "Sistema operacional não suportado"
            exit 1
        fi
    fi
}

function install_frameweb_vp_plugin(){
    # Instala o plugin com o maven
    mvn install
    # Verificar se o plugin foi instalado com sucesso
    if [ -d "/Users/$USER/Library/Application Support/VisualParadigm/plugins" ]; then
        echo "O plugin foi instalado com sucesso."
    else
        echo "A instalação do plugin falhou."
        exit 1
    fi

    if [[ "$OSTYPE" == "darwin"* ]]; then
        # Instalação no Mac
        install_brew
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Instalação no Linux
        echo ""
    fi
}

function install_visual_paradigm(){
    echo "Opening Visual Paradigm WebSite"
    open https://www.visual-paradigm.com/download/
}

function install_brew(){
    # Check if homebrew is installed
    if [ ! command -v brew &> /dev/null ]; then
        echo "Installing Homebrew ..."
        # if it's is not installed, then install it.
        /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/uninstall.sh)" 
        # Check for what arcitecture, so you can place path.
        if [[ "uname -m" == "x86_64" ]]; then
            echo "export PATH=/usr/local/bin:$PATH" >> ~/.bash_profile && source ~/.bash_profile
        fi
    # If not
    else
        # Print that it's already installed
        echo "Homebrew is already installed"
    fi
}

# Function to install for debian based distros (and ubuntu)
function install_shell_deps(){ 
    if [[ "$OSTYPE" == "darwin"* ]]; then
        # Instalação no Mac
        install_brew
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        # Instalação no Linux
        echo "No shell required dependencies on linux"
    fi
} 

# Main function
function install_main(){ 
    #Defining the path
    echo "=== DEBUG MODE ==="
    get_VP_App_Path
    get_VP_Plugin_Path
    echo "=== Frameweb-vp-plugin Installer ==="
    echo "Installing shell dependencies ..."
    install_shell_deps || install_fail
    echo "Installing plugin dependencies ..."
    install_jdk || install_fail
    install_maven || install_fail
    #install_visual_paradigm || install_fail
    #echo "Installing frameweb-vp-plugin..."
    #install_frameweb_vp_plugin || install_fail
}

# Run the main function
install_main