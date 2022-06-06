import React, {useState, useEffect} from 'react';
import './App.css';

import axios from 'axios';

import AceEditor from "react-ace";

import "ace-builds/src-noconflict/mode-c_cpp";
import "ace-builds/src-noconflict/theme-monokai";



function App() {
  const [code1, setCode1] = useState("#include <iostream>\n\
using namespace std;\n\
\n\
int main() {\n\
    cout << \"Hello, World!\";\n\
    return 0;\n\
}")
  const [code2, setCode2] = useState("")

  const compile = () => {
    setCode2("Compiling...")
    axios.post('http://localhost:8000/compiler/', {
      code: code1
    })
    .then((response) => {
      setCode2(response.data);
    })
    .catch((error) => {
      console.log(error);
    })
  }


  return (
    <div className="app">
      <div className="editor">
      <AceEditor
        className="code1"
        mode="c_cpp"
        theme="monokai"
        value={code1}
        onChange={setCode1}
        name="editor"
        editorProps={{ $blockScrolling: true }}
      />
      <AceEditor
        className="code2"
        mode="c_cpp"
        theme="monokai"
        value={code2}
        name="editor"
        readOnly={true}
        editorProps={{ $blockScrolling: true }}
      />
      </div>
      <div className="help" onClick={()=>compile()}>
        Compile
      </div>
    </div>
  );
}

export default App;
