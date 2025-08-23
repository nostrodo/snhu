// Title: Course Info BST, by Mitchell Flint
// Class: DSA: Analysis and Design
// Teacher: Alex Cohen
// Date: 8/16/2025


#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

// course info: number, name, prereqs
struct Course {
    string courseNumber;
    string courseName;
    vector<string> prerequisites;
};

// BST node
struct BSTNode {
    Course data;
    BSTNode* left = nullptr;
    BSTNode* right = nullptr;
    explicit BSTNode(const Course& c) : data(c) {}
};

// BST wrapper
struct BST {
    BSTNode* root = nullptr;

    // adding a course to the tree
    bool insert(const Course& c) {
        if (!root) { root = new BSTNode(c); return true; }

        BSTNode* cur = root;

        while (true) {
            if (c.courseNumber == cur->data.courseNumber) {
                return false; //ignore duplicate
            }
            else if (c.courseNumber < cur->data.courseNumber) {
                if (!cur->left) { cur->left = new BSTNode(c); return true; }

                cur = cur->left;
            }
            else {
                if (!cur->right) { cur->right = new BSTNode(c); return true; }

                cur = cur->right;
            }
        }
    }

    // look up by course number
    const Course* find(const string& key) const {
        const BSTNode* cur = root;

        while (cur) {
            if (key == cur->data.courseNumber) return &cur->data;
            cur = (key < cur->data.courseNumber) ? cur->left : cur->right;
        }
        return nullptr;
    }

    // print all courses in order
    void printInOrder() const { printInOrderRec(root); }

private:
    static void printInOrderRec(const BSTNode* node) {
        if (!node) return;

        printInOrderRec(node->left);
        cout << node->data.courseNumber << ", " << node->data.courseName << "\n";
        printInOrderRec(node->right);
    }
};

// split the CSV
static vector<string> splitCSV(const string& line) {
    vector<string> parts;
    string token;
    stringstream ss(line);

    while (getline(ss, token, ',')) {
        parts.push_back(token);
    }
    return parts;
}

// load the file and manually validate
static vector<string> loadCourseFile(const string& filename) {
    vector<string> lines;

    ifstream in(filename);
    
    // return empty string on error
    if (!in) {
        cout << "Error: could not open file.\n";
        return {};
    }

    string line;
    while (getline(in, line)) {
        if (!line.empty()) lines.push_back(line);
    }
    in.close();

    // check that lines have number and name
    for (auto& ln : lines) {
        auto parts = splitCSV(ln);
        if (parts.size() < 2) {
            cout << "Error: missing number or name.\n";
            return {};
        }
    }

    //check that each prereq exists as some course's number
    for (auto& ln : lines) {
        auto parts = splitCSV(ln);

        for (size_t i = 2; i < parts.size(); ++i) {
            const string& prereqNum = parts[i];

            // don't flag error if course has no prereqs
            if (prereqNum.empty()) continue;

            bool found = false;

            // search for matching course number
            for (auto& checkLine : lines) {
                auto checkParts = splitCSV(checkLine);
                if (!checkParts.empty() && checkParts[0] == prereqNum) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                cout << "Error: prereq not found in file: " << prereqNum << "\n";
                return {};
            }
        }
    }

    return lines;
}

// turn the lines into a BST 
static BST createCourseTree(const vector<string>& lines) {
    BST tree;
    for (auto& ln : lines) {
        auto parts = splitCSV(ln);
        Course c;
        c.courseNumber = parts[0];
        c.courseName = parts[1];
        
        for (size_t i = 2; i < parts.size(); ++i) {
            if (!parts[i].empty()) {
                c.prerequisites.push_back(parts[i]);
            }
        }
        tree.insert(c);
    }
    return tree;
}

// print all courses
static void printAllCourses(const BST& coursesTree) {
    cout << "Here is a sample schedule:\n\n";
    coursesTree.printInOrder();
}

// print one courses and its prereqs
static void searchCourse(const BST& coursesTree, const string& id) {
    const Course* c = coursesTree.find(id);

    if (!c) {
        cout << "Course not found.\n";
        return;
    }

    cout << c->courseNumber << ", " << c->courseName << "\n";
    if (c->prerequisites.empty()) {
        cout << "No Prerequsites\n";
    }
    else {
        cout << "Prerequisites: ";
        for (size_t i = 0; i < c->prerequisites.size(); ++i) {
            cout << c->prerequisites[i];
            if (i + 1 < c->prerequisites.size()) cout << ", ";
        }
        cout << "\n";
    }
}



// menu loop
int main()
{
    cout << "Welcome to the course planner\n\n";

    // initial variables
    bool dataLoaded = false;
    BST coursesTree;
    string input;
    int choice = 0;

    while (choice != 9) {
        // main menu
        cout << "1. Load Data Structure.\n";
        cout << "2. Print Course List.\n";
        cout << "3. Print Course.\n";
        cout << "9. Exit\n\n";
        cout << "What would you like to do? ";

        getline(cin, input);

        // handle invalid input
        try {
            choice = stoi(input);
        }
        catch (...) {
            cout << "Invalid option\n\n";
            continue;
        }

        switch (choice) {
            // Option 1: load data
            case 1: {

                // simple data leak prevention
                if (dataLoaded) {
                    cout << "Data is already loaded. \n\n";
                    break;
                }

                const string filename = "./CS 300 ABCU_Advising_Program_Input.csv";

                auto lines = loadCourseFile(filename);
                if (!lines.empty()) {
                    coursesTree = createCourseTree(lines);
                    dataLoaded = true;
                    cout << "Data loaded from " << filename << "\n\n";
                }
                else {
                    dataLoaded = false;
                    cout << "Error loading from " << filename << "\n\n";
                }
                break;
            }

            // Option 2: Print Course List
            case 2: {
                if (!dataLoaded) {
                    cout << "Please load data first.\n\n";
                }
                else {
                    printAllCourses(coursesTree);
                    cout << "\n";
                }
                break;
            }


            //Option 3: Print Course
            case 3: {
                if (!dataLoaded) {
                    cout << "Please load data first\n\n";
                }
                else {
                    cout << "What course do you want to know about? ";
                    string id;
                    getline(cin, id);
                    cout << "\n";

                    // nprmalize input into uppercase
                    for (char &ch : id) {
                        ch = static_cast<char>(toupper(static_cast<unsigned char>(ch)));
                    }


                    searchCourse(coursesTree, id);
                    cout << "\n";
                }
                break;
            }

            // es-ca-pe. funny, spelled just like the word escape
            case 9:
                cout << "Thank you for using the course planner!\n";
                break;


            default:
                cout << "Invalid option\n\n";
                break;
        }
    }

    return 0;
}
