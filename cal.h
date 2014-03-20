#include <stack>
#include <string>

using namespace std;

class Calculator{
public:
	stack<string> *oprd, *optr;
	string ans;
	string var[100];
public:
	Calculator(){
		ans = "";
		oprd = new stack<string>;
		optr = new stack<string>;
		optr->push("#");
	}
	~Calculator(){
		delete oprd;
		delete optr;
	}
	string process(string);
	string get();
	char compare(string, string);
	string cal(string, string);
	string cal(string, string, string);
	bool save(const char[]);
	string load(const char[]);
	unsigned int getidx(string);
};