#include "stdafx.h"

CComModule _Module;
JNIModule jniModule;

BOOL APIENTRY DllMain( HINSTANCE hModule, 
                       DWORD  dwReason, 
                       LPVOID lpReserved
					 )
{
	// sanity check
	_ASSERT( sizeof(void*)==sizeof(jint) );
	_Module.DllMain(hModule, dwReason, lpReserved, NULL, NULL );
//    _Module.Init(ObjectMap, m_hInstance, &LIBID_LIB2USRLib);

	return TRUE;
}

#ifdef _DEBUG

STDAPI DllCanUnloadNow(void)
{
  return (_Module.GetLockCount()==0) ? S_OK :S_FALSE;
}

STDAPI DllGetClassObject(REFCLSID rclsid, REFIID riid, LPVOID* ppv)
{
  return _Module.GetClassObject(rclsid, riid, ppv);
}

STDAPI DllRegisterServer(void)
{
  return _Module.RegisterServer(TRUE);
}

STDAPI DllUnregisterServer(void)
{
  return _Module.UnregisterServer(TRUE);
}
#endif